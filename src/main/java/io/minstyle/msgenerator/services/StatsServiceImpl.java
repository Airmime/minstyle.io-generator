package io.minstyle.msgenerator.services;

import io.minstyle.msgenerator.model.CustomCSSModel;
import io.minstyle.msgenerator.model.StatsModel;
import io.minstyle.msgenerator.repository.CustomCSSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Service implementation allowing get stats.
 *
 * @author Rémi Marion
 */
@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    CustomCSSRepository customCSSRepository;

    /**
     * Get downloads per day.
     *
     * @param minusDays Number days to subtract.
     * @return List of CSS generated.
     */
    @Override
    public StatsModel countDownloadMinusDays(int minusDays) {
        StatsModel stats = new StatsModel();

        Map<String, Integer> datas = new HashMap<>();
        ArrayList<String> libelle = new ArrayList<>();

        /* Data Concatenation */
        for (CustomCSSModel customCSS : customCSSRepository.getDateGreaterThan(LocalDateTime.now().minusDays(minusDays))) {
            String day = String.valueOf(customCSS.getCreated().getDayOfMonth());
            String month = String.valueOf(customCSS.getCreated().getMonthValue());
            String date = day + "/" + month;

            if (datas.containsKey(date)) {
                datas.put(date, 1 + datas.get(date));

            } else {
                datas.put(date, 1);
                libelle.add(date);
            }
        }

        /* Generate result */
        ArrayList<String> datasResult = new ArrayList<>();
        for (String lib : libelle) {
            datasResult.add(String.valueOf(datas.get(lib)));
        }

        /* Consolidation of return object */
        stats.setLibelleList(libelle);
        stats.setDatasList(datasResult);

        return stats;
    }

    /**
     * Get downloads per months.
     *
     * @param minusMonths Number months to subtract.
     * @return List of CSS generated.
     */
    @Override
    public StatsModel countDownloadMinusMonths(int minusMonths) {
        StatsModel stats = new StatsModel();

        Map<String, Integer> datas = new HashMap<>();
        ArrayList<String> libelle = new ArrayList<>();

        /* Data Concatenation */
        for (CustomCSSModel customCSS : customCSSRepository.getDateGreaterThan(LocalDateTime.now().minusMonths(minusMonths))) {
            String month = String.valueOf(customCSS.getCreated().getMonth());

            if (datas.containsKey(month)) {
                datas.put(month, 1 + datas.get(month));

            } else {
                datas.put(month, 1);
                libelle.add(month);
            }
        }

        /* Generate result */
        ArrayList<String> datasResult = new ArrayList<>();
        for (String lib : libelle) {
            datasResult.add(String.valueOf(datas.get(lib)));
        }

        /* Consolidation of return object */
        stats.setLibelleList(libelle);
        stats.setDatasList(datasResult);

        return stats;
    }

    /**
     * Gets trends colors.
     *
     * @param minusMonths Number months to subtract.
     * @return CssModel sort by trend.
     */
    @Override
    public Map<CustomCSSModel, Integer> getTrends(int minusMonths) {

        List<CustomCSSModel> datas = customCSSRepository.getDateGreaterThan(LocalDateTime.now().minusMonths(minusMonths));
        TreeMap<CustomCSSModel, Integer> countMapTree = new TreeMap<>();

        for (CustomCSSModel cssObject : datas) {
            CustomCSSModel customCSSModel = new CustomCSSModel(cssObject.getPrimaryColor(), cssObject.getSecondaryColor(), cssObject.getActionColor(), cssObject.getAction2Color());
            if (countMapTree.containsKey(customCSSModel)) {
                countMapTree.put(customCSSModel, countMapTree.get(customCSSModel) + 1);
            } else {
                countMapTree.put(cssObject, 1);
            }
        }

        countMapTree.entrySet().stream().sorted();

        return countMapTree;
    }
}
