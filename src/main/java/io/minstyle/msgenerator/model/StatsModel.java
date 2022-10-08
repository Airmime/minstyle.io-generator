package io.minstyle.msgenerator.model;

import lombok.Data;

import java.util.ArrayList;

/**
 * Objet allow to get stats.
 *
 * @author Rémi Marion
 */
@Data
public class StatsModel {
    ArrayList<String> datasList;
    ArrayList<String> libelleList;
}
