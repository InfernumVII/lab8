package client.view.visualizationScope;


import javafx.scene.control.TableView;
import shared.collection.Dragon;

public class VisualizationScopeController {
    private final DragonMap dragonMap;
    private final TableView<Dragon> tableView;

    public VisualizationScopeController(TableView<Dragon> dragonTable) {
        dragonMap = new DragonMap(0.10, 500, 500);
        tableView = dragonTable;
    }

    public void updateVisualScope() {
        dragonMap.syncWithList(tableView.getItems());
    }

    public void show() {
        dragonMap.show();
    }

}
