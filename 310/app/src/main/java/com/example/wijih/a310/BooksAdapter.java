package com.example.wijih.a310;

        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.support.annotation.NonNull;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.widget.RecyclerView;
        import android.util.Base64;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.wijih.a310.model.Book;
        import com.example.wijih.a310.model.User;

        import java.io.IOException;
        import java.util.List;


public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.MatchesViewHolder> {
    private List<Book> booksList;
    private Context context;
    private User currentUser;

    public BooksAdapter(List<Book> booksList, Context context, User currentUser) {
        this.booksList = booksList;
        this.context = context;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_books_view, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        final MatchesViewHolder rView = new MatchesViewHolder(layoutView, context);

        return rView;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {

        final int pos = position;
        if (booksList.get(position).getImageString() != null) {
            try {
                Bitmap map = decodeFromFirebase(booksList.get(position).getImageString());
                holder.bookImg.setImageBitmap(map);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        holder.bookTitle.setText(booksList.get(position).getTitle());
        holder.bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditBookActivity.class);
                intent.putExtra("current_user", currentUser);
                intent.putExtra("bookId", booksList.get(pos).getBookId());
                view.getContext().startActivity(intent);
                Toast.makeText(view.getContext(), "Button was clicked for list item " + booksList.get(pos).getBookId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public static Bitmap decodeFromFirebase(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }

    class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView bookTitle, bookDescription;
        public Button bookBtn;
        public ImageView bookImg;

        public MatchesViewHolder(View itemView, Context context) {
            super(itemView);
            itemView.setOnClickListener(this);

            bookTitle = itemView.findViewById(R.id.bookTitle);
            bookImg = itemView.findViewById(R.id.bookImg);
            bookBtn = itemView.findViewById(R.id.listItemBtn);
        }

        @Override
        public void onClick(View view) {

        }
    }
}