package com.example.yupiProjet.Services;

import com.example.yupiProjet.Dto.PointHistoriqueDto;
import com.example.yupiProjet.Models.Commande;
import com.example.yupiProjet.Models.LigneCommande;
import com.example.yupiProjet.Enum.StatutCommande;
import com.example.yupiProjet.Repository.CommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatistiqueService {

    private final CommandeRepository commandeRepository;

    public enum Periode { JOUR, SEMAINE, MOIS, ANNEE }

    public BigDecimal calculerBenefice(Periode periode) {
        return commandesLivrees(periode).stream()
                .map(this::beneficeNetCommande)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int calculerNombreProduitsVendus(Periode periode) {
        return commandesLivrees(periode).stream()
                .flatMap(c -> c.getLignes().stream())
                .mapToInt(LigneCommande::getQuantite)
                .sum();
    }

    // Somme des points PV bruts (indépendant du bénéfice en FCFA)
    public BigDecimal calculerPvTotal(Periode periode) {
        return commandesLivrees(periode).stream()
                .flatMap(c -> c.getLignes().stream())
                .map(ligne -> ligne.getProduit().getNombrePv()
                        .multiply(BigDecimal.valueOf(ligne.getQuantite())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal beneficeNetCommande(Commande commande) {
        BigDecimal beneficeBrut = commande.getLignes().stream()
                .map(LigneCommande::getBeneficeLigne)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return beneficeBrut.subtract(commande.getFraisLivraison());
    }

    private List<Commande> commandesLivrees(Periode periode) {
        LocalDate[] bornes = resoudrePeriode(periode);
        return commandeRepository.findLivreesEntre(StatutCommande.LIVRE, bornes[0], bornes[1]);
    }

    private LocalDate[] resoudrePeriode(Periode periode) {
        LocalDate aujourdHui = LocalDate.now();
        LocalDate debut = switch (periode) {
            case JOUR -> aujourdHui;
            case SEMAINE -> aujourdHui.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
            case MOIS -> aujourdHui.withDayOfMonth(1);
            case ANNEE -> aujourdHui.withDayOfYear(1);
        };
        return new LocalDate[]{debut, aujourdHui};
    }

    /**
     * Série de points pour le graphique :
     * - SEMAINE : un point par jour (Lun → Dim de la semaine en cours)
     * - MOIS    : un point par jour du mois en cours
     * - ANNEE   : un point par mois (Jan → Déc)
     */
    public List<PointHistoriqueDto> genererHistorique(Periode periode) {
        List<Commande> commandes = commandesLivrees(periode);

        Map<String, BigDecimal> parCle = new LinkedHashMap<>();
        List<String> ordreCles = genererCles(periode);
        ordreCles.forEach(cle -> parCle.put(cle, BigDecimal.ZERO));

        for (Commande commande : commandes) {
            String cle = cleDe(commande.getDateLivraisonEffective(), periode);
            BigDecimal actuel = parCle.getOrDefault(cle, BigDecimal.ZERO);
            parCle.put(cle, actuel.add(beneficeNetCommande(commande)));
        }

        return ordreCles.stream()
                .map(cle -> new PointHistoriqueDto(cle, parCle.get(cle)))
                .collect(Collectors.toList());
    }

    private List<String> genererCles(Periode periode) {
        LocalDate[] bornes = resoudrePeriode(periode);
        List<String> cles = new ArrayList<>();

        if (periode == Periode.ANNEE) {
            for (int mois = 1; mois <= LocalDate.now().getMonthValue(); mois++) {
                cles.add(java.time.Month.of(mois).getDisplayName(TextStyle.SHORT, Locale.FRENCH));
            }
        } else {
            LocalDate curseur = bornes[0];
            while (!curseur.isAfter(bornes[1])) {
                cles.add(cleDe(curseur, periode));
                curseur = curseur.plusDays(1);
            }
        }
        return cles;
    }

    private String cleDe(LocalDate date, Periode periode) {
        if (date == null) return "N/A";
        return switch (periode) {
            case ANNEE -> date.getMonth().getDisplayName(TextStyle.SHORT, Locale.FRENCH);
            default -> date.getDayOfMonth() + "/" + date.getMonthValue();
        };
    }
}
