package io.minstyle.msgenerator.controller;

import io.minstyle.msgenerator.model.CustomCSSModel;
import io.minstyle.msgenerator.model.StatsModel;
import io.minstyle.msgenerator.services.StatsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Manage stats controller.
 *
 * @author Rémi Marion
 */
@RestController
public class StatsController {

    @Autowired
    StatsServiceImpl statsServiceImpl;

    @GetMapping("stats/weekly")
    public ResponseEntity<StatsModel> getDownloadsFromLastWeek() {
        return ResponseEntity.ok(statsServiceImpl.countDownloadMinusDays(7));
    }

    @GetMapping("stats/monthly")
    public ResponseEntity<StatsModel> getDownloadsFromLastMonth() {
        return ResponseEntity.ok(statsServiceImpl.countDownloadMinusDays(30));
    }

    @GetMapping("stats/years")
    public ResponseEntity<StatsModel> getDownloadsFromLastYears() {
        return ResponseEntity.ok(statsServiceImpl.countDownloadMinusMonths(12));
    }

    @GetMapping("/trends/year")
    public ResponseEntity<Map<CustomCSSModel, Integer>> getTrendsCurrentYear() {
        return ResponseEntity.ok(statsServiceImpl.getTrends(12));
    }
}
