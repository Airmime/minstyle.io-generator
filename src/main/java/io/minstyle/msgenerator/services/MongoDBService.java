package io.minstyle.msgenerator.services;

/**
 * Service allowing to interact with the DB.
 *
 * @author Rémi Marion
 * @version 0.0.1
 */
public interface MongoDBService<T> {

	/**
	 * Write in MongoDB Database.
	 * @param obj Object to write.
	 * @return Object wrote.
	 */
	T writeInDB(T obj);
}
