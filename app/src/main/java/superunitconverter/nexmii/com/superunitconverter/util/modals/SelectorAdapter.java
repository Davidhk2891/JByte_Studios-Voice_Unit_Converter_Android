package superunitconverter.nexmii.com.superunitconverter.util.modals;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import superunitconverter.nexmii.com.superunitconverter.R;
import superunitconverter.nexmii.com.superunitconverter.util.ResultColor;

/**
 * Adapter for the Selector menu
 */
public class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.SelectorHolder> {

    private final List<String> menuList;
    private OnItemClickListener listener;

    public SelectorAdapter(List<String> menuList) {
        this.menuList = menuList;
    }

    /**
     * Adapter's onCreateViewHolder. Its job is to fire up the XML file which holds the views
     */
    @NonNull
    @Override
    public SelectorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selector_item, parent, false);
        return new SelectorHolder(view);
    }

    /**
     * Adapter's inBindViewHolder. Its job is to connect the XML views from the XML
     * to the iner class variables of the same type (the viewHolder class)
     */
    @Override
    public void onBindViewHolder(@NonNull SelectorHolder holder, int position) {
        holder.selectorItemContent.setText(menuList.get(position));
        setElementsColor(menuList.get(position), holder.selectorItemContent);
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    @Override
    public int getItemCount() {
        return menuList.size();
    }

    /**
     * Method for setting the color for the selector item
     */
    private void setElementsColor(String category, TextView categoryView){
        categoryView.setTextColor(ResultColor.getInstance().generalColorSelector(categoryView.getContext(), category));
    }

    /**
     * Inner class that extends the RecyclerView.ViewHolder class.
     * This is used to hold the view variables here instead of doing
     * it in the adapter itself which allows for
     */
    public class SelectorHolder extends RecyclerView.ViewHolder{

        private final TextView selectorItemContent;

        public SelectorHolder(View itemView){
            super(itemView);
            selectorItemContent = itemView.findViewById(R.id.selector_item_content);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(menuList.get(position));
                }
            });

        }

    }

    public interface OnItemClickListener {
        void onItemClick(String text);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
