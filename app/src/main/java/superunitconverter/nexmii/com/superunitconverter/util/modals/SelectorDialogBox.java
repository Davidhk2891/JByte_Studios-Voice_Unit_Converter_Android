package superunitconverter.nexmii.com.superunitconverter.util.modals;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import superunitconverter.nexmii.com.superunitconverter.R;
import superunitconverter.nexmii.com.superunitconverter.models.unitmanager.UnitLists;

/**
 * Singleton class for generating the app's category selector dialog.
 * This is used by the manual converter to select categories and units.
 */
public class SelectorDialogBox {

    private static SelectorDialogBox dialogBoxInstance;
    private SelectorAdapter selectorAdapter;
    private Dialog dialog;
    private final UnitLists unitLists;
    private List<String> correctList;
    private RecyclerView selectorRv;

    private SelectorDialogBox(){
        unitLists = UnitLists.getInstance();
    }

    public static SelectorDialogBox getDialogBoxInstance(){
        if (dialogBoxInstance == null)
            dialogBoxInstance = new SelectorDialogBox();
        return dialogBoxInstance;
    }

    /**
     * dialog getter for public access
     */
    public Dialog getDialog(){
        return dialog;
    }

    /**
     * Main method for bulding and fire up a custom list dialog box.
     * It populates the list differently depending heavely on the parameters passed.
     */
    public void buildMenuDialogBox(Context ctx, Activity act, String menuTitle, String category
            , DialogBoxResultInterface dbri){
        //Dialog initialization
        dialog = new Dialog(act);

        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.selector_custom_dialog);

        //Dialog title
        TextView selectorTitle = dialog.findViewById(R.id.selector_title);
        selectorTitle.setText(menuTitle);

        //Dialog recycler view
        selectorRv = dialog.findViewById(R.id.selector_rv);
        selectorRv.setLayoutManager(new LinearLayoutManager(ctx));
        selectorRv.setHasFixedSize(true);

        //Display goes here
        selectorAdapter = null;
        if ((menuTitle.equals(ctx.getResources().getString(R.string.original_unit)))) {
            //If the items pressed is ORIGINAL UNIT
            displayedSubMenuFromCorrectCategory(category, menu -> {
                selectorAdapter = new SelectorAdapter(menu);
                setupAdapter(dbri);
            });
        } else if (menuTitle.equals(ctx.getResources().getString(R.string.target_unit))) {
            //If the items pressed is TARGET UNIT
            displayedSubMenuFromCorrectCategory(category, menu -> {
                selectorAdapter = new SelectorAdapter(menu);
                setupAdapter(dbri);
            });
        } else {
            //If the item passed is CATEGORY
            displayedCategoryMenu(menu -> {
                selectorAdapter = new SelectorAdapter(menu);
                setupAdapter(dbri);
            });
        }
        dialog.show();
    }

    /**
     * Sets up the recycler view adapter within the dialog box and also sets up the single item
     * click listener.
     */
    private void setupAdapter(DialogBoxResultInterface dbri){
        selectorRv.setAdapter(selectorAdapter);
        selectorAdapter.notifyDataSetChanged();
        selectorAdapter.setOnItemClickListener(dbri::onMenuItemClicked);
    }

    /**
     * Displays the category menu for the current amount of available categories
     */
    private void displayedCategoryMenu(DialogBoxInterface dbi){
        UnitLists.getInstance().getConversionsList().clear();
        dbi.onBoxReadyToShow(UnitLists.getInstance().getConversionsList());
    }

    /**
     * This method is required both in this class and in ManualConversionFrag
     * Depending on the selected category, it will display the correct menu in the unit dialog
     * boxes (recycler views).
     */
    public void displayedSubMenuFromCorrectCategory(String category, DialogBoxInterface dbi){
        correctList = new ArrayList<>();
        switch (category){
            case "temperature":
                unitLists.getExactSTempUnitList().clear();
                correctList = unitLists.getExactSTempUnitList();
                dbi.onBoxReadyToShow(correctList);
                break;
            case "length":
                unitLists.getExactSLengthUnitList().clear();
                correctList = unitLists.getExactSLengthUnitList();
                dbi.onBoxReadyToShow(correctList);
                break;
            case "weight":
                unitLists.getExactSWeightUnitList().clear();
                correctList = unitLists.getExactSWeightUnitList();
                dbi.onBoxReadyToShow(correctList);
                break;
            case "volume":
                unitLists.getExactSVolumeUnitList().clear();
                correctList = unitLists.getExactSVolumeUnitList();
                dbi.onBoxReadyToShow(correctList);
                break;
            case "currency":
                displayedCurrencyNames(currencies -> {
                    correctList.clear();
                    correctList = currencies;
                    dbi.onBoxReadyToShow(correctList);
                });
                break;
        }
    }

    //Currencies
    /**
     * Dedicated method for populating all available currencies in the dialog box's recycler
     * view.
     */
    private void displayedCurrencyNames(CurrencySubMenu currencySubMenu){
        List<String> currencyNames = new ArrayList<>();
        unitLists.getCurrenciesList((currenciesList -> {
            for (Map.Entry<String, String> entry : currenciesList.entrySet()){
                currencyNames.add(entry.getValue());
            }
            currencySubMenu.onCurrencyListReady(currencyNames);
        }), null);
    }

    /**
     * Dedicated Callback interface for triggering an action when the currency list is ready
     */
    public interface CurrencySubMenu{
        void onCurrencyListReady(List<String> currencies);
    }

    /**
     * Callback interface for triggering actions when the dialogbox is displayed
     */
    public interface DialogBoxInterface{
        void onBoxReadyToShow(List<String> menu);
    }

    /**
     * Callback interface for single item clicked in recycler view from dialog boxes
     */
    public interface DialogBoxResultInterface{
        void onMenuItemClicked(String clickedItem);
    }
}
