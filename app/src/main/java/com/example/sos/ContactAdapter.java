package com.example.sos;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    private List<ContactModel> contactList;
    private OnDeleteClickListener deleteClickListener;

    public ContactAdapter(List<ContactModel> contactList, OnDeleteClickListener deleteClickListener) {
        this.contactList = contactList;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view, deleteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactModel contact = contactList.get(position);
        holder.nameText.setText(contact.getName());
        holder.phoneText.setText(contact.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView nameText, phoneText;
        ImageButton deleteButton;

        ContactViewHolder(@NonNull View itemView, OnDeleteClickListener deleteClickListener) {
            super(itemView);
            nameText = itemView.findViewById(R.id.contact_name);
            phoneText = itemView.findViewById(R.id.contact_phone);
            deleteButton = itemView.findViewById(R.id.delete_button);

            deleteButton.setOnClickListener(v -> {
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(getAdapterPosition());
                }
            });
        }
    }
}