package client.view.visualizationScope;


import javafx.scene.control.TableView;
import shared.collection.Dragon;

public class VisualizationScopeController {
    private final DragonMap dragonMap;
    private final TableView<Dragon> tableView;
    private Dragon lastCheckDragon = null;

    public VisualizationScopeController(TableView<Dragon> dragonTable) {
        dragonMap = new DragonMap(0.10, 500, 500);
        tableView = dragonTable;
    }

    public void updateVisualScope() {
        dragonMap.syncWithList(tableView.getItems());

        Dragon selectedDragon = dragonMap.getCheckDragon();
        if (selectedDragon != null && lastCheckDragon != selectedDragon) {
            tableView.getSelectionModel().select(selectedDragon);
            lastCheckDragon = selectedDragon;
        }
    }

    public void show() {
        dragonMap.show();
    }

    public void close() {
        dragonMap.close();
    }
}
