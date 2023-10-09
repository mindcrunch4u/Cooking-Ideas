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
import androidx.lifecycle.ViewModelProvider;

import com.bob.info.R;
import com.bob.info.databinding.FragmentCookBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CookFragment extends Fragment implements View.OnClickListener {

    private CookViewModel cookViewModel = null;

    ImageButton button_copy;
    ImageButton button_renew;
    TextView text_view;
    TextView cook_text;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cook, container, false);
        button_copy = (ImageButton)root.findViewById(R.id.button_copy);
        button_copy.setOnClickListener((View.OnClickListener) this);
        button_renew = (ImageButton)root.findViewById(R.id.button_renew);
        button_renew.setOnClickListener((View.OnClickListener) this);

        text_view = (TextView)root.findViewById(R.id.text_cook);
        text_view.setMovementMethod(new ScrollingMovementMethod());

        cook_text = root.findViewById(R.id.text_cook);
        cook_text.setText("今天煮什么");

        return root;
    }
    private void setClipboard(Context context, String text) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_copy){
            String currentText = cook_text.getText().toString();
            setClipboard(getContext(), currentText);
            Toast.makeText(getContext(), "copied", Toast.LENGTH_SHORT).show();
        }
        else if (v.getId() == R.id.button_renew){
            String bigString = "" + System.lineSeparator();
            ArrayList<ArrayList<String>> basket = new ReceiptGenerator().generate();
            for( ArrayList list : basket ){
                if (list.get(0) == "none" || list.get(1) == ""){
                    continue;
                }
                bigString += list.get(0);
                bigString += ":";
                bigString += String.join(", ", list.subList(1, list.size()));
                bigString += System.lineSeparator();
            }
            cook_text.setText(bigString);
        }
    }
}