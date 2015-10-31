package sf.net.dvstar.insugraph.database;

/**
 * Created by sdv on 24.10.15.
 */
public interface ActionCommonItem {

    int ACTION_TYPE_INJECT = 1;
    int ACTION_TYPE_GLUCOSE = 2;
    int ACTION_TYPE_INSULIN = 3;

    String getListText();
    int getActionType();
}
