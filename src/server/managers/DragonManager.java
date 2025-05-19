package server.managers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import shared.collection.Dragon;
import shared.network.models.Pair;
import shared.network.models.User;
import server.ServerMain;
import server.managers.exceptions.DragonFindException;
import server.psql.dragon.DragonDB;


public class DragonManager {
    private Set<Dragon> dragonSet;
    private LocalDate initializationDate;
    private static DragonDB dragonDBInstance = new DragonDB(ServerMain.getManagerInsance().getConnection());
    public DragonManager() {
        dragonSet = Collections.synchronizedSet(new LinkedHashSet<>());
        initializationDate = LocalDate.now();
        loadFromDatabase(); 
    }

    private synchronized void loadFromDatabase() {
        List<Dragon> dbDragons = dragonDBInstance.getAllDragons();
        dragonSet.addAll(dbDragons);
        System.out.println("Загружено " + dbDragons.size() + " драконов из БД");
    }
    

    public String getTypeName(){
        return dragonSet.getClass().getSimpleName();
    }

    public synchronized Pair<Integer, Integer> preAddDragon(Dragon e, User user){
        return dragonDBInstance.insertDragon(e, user);
    }

    public synchronized void addDragon(Dragon e){
        dragonSet.add(e);
    }

    public synchronized boolean setHaveId(int id) {
        return dragonSet.stream()
                .anyMatch(dragon -> dragon.getId() == id);
    }

    public synchronized Dragon returnDragonById(int id) throws DragonFindException {
        return dragonSet.stream()
                .filter(dragon -> dragon.getId() == id)
                .findFirst()
                .orElseThrow(() -> new DragonFindException());
    }
    
    public synchronized Set<Dragon> getDragonSet() {
        return dragonSet;
    }

    public synchronized List<Dragon> getSortedDragons() {
        return dragonSet.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public synchronized Dragon getMinDragonByXAndY(){
        Dragon minDragon = Collections.min(getDragonSet(), new Comparator<Dragon>() {
                @Override
                public int compare(Dragon d1, Dragon d2) {
                    return Long.compare(d1.getCoordinates().getY()+d1.getCoordinates().getX(), d2.getCoordinates().getY()+d2.getCoordinates().getX());
                }
            });
        return minDragon;
    }

    public synchronized boolean preClearDragonSet(User user){
        return dragonDBInstance.deleteDragonsWithAuth(user);
    }

    public synchronized void clearDragonSet(User user){
        int userId = ServerCommandManager.getAuthInstance().findUserId(user);
        dragonSet.removeIf(dragon -> dragon.getOwnerId() == userId);
    }

    public synchronized boolean preRemoveDragon(Dragon e, User user){
        return dragonDBInstance.deleteDragonsWithAuth(user, e.getId());
    }

    public synchronized void removeDragon(Dragon e){
        dragonSet.remove(e);
    }

    public LocalDate getInitializationDate() {
        return initializationDate;
    }
}
