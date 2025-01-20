package com.bob.info.ui.cook;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bob.info.R;
import java.util.ArrayList;
import java.util.Objects;

public class CookFragment extends Fragment implements View.OnClickListener {

    ImageButton button_copy;
    ImageButton button_renew;
    TextView cook_text;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cook, container, false);
        button_copy = root.findViewById(R.id.button_copy);
        button_copy.setOnClickListener(this);
        button_renew = root.findViewById(R.id.button_renew);
        button_renew.setOnClickListener(this);

        cook_text = root.findViewById(R.id.text_cook);
        cook_text.setMovementMethod(new ScrollingMovementMethod());
        cook_text.setText(getString(R.string.fragment_cook_title));

        return root;
    }

    private void setClipboard(Context context, String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_copy) {
            String currentText = cook_text.getText().toString();
            setClipboard(getContext(), currentText);
            Toast.makeText(getContext(), getString(R.string.util_copied_text), Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.button_renew) {
            StringBuilder bigString = new StringBuilder(System.lineSeparator());
            ArrayList<ArrayList<String>> basket = new ReceiptGenerator(getContext()).generate();
            for (ArrayList<String> list : basket) {
                if (Objects.equals(list.get(0), "none") || Objects.equals(list.get(1), "")) {
                    continue;
                }
                bigString.append(list.get(0));
                bigString.append(":");
                bigString.append(String.join(", ", list.subList(1, list.size())));
                bigString.append(System.lineSeparator());
            }
            cook_text.setText(bigString.toString());
        }
    }
}