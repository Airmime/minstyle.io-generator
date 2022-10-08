package io.minstyle.msgenerator.services;

import io.minstyle.msgenerator.model.CustomCSSModel;
import io.minstyle.msgenerator.model.StatsModel;

import java.util.Map;

/**
 * Service allowing to get stats.
 *
 * @author Rémi Marion
 */
public interface StatsService {

    StatsModel countDownloadMinusDays(int minusDays);

    StatsModel countDownloadMinusMonths(int minusMonths);

    Map<CustomCSSModel, Integer> getTrends(int minusMonths);
}
