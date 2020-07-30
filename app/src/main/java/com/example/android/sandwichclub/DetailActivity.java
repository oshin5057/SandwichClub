package com.example.android.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sandwichclub.model.Sandwich;
import com.example.android.sandwichclub.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    public static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnownLabelTV;
    private TextView mAlsoKnownTV;

    private TextView mPlaceOfOriginLabelTV;
    private TextView mOriginTV;

    private TextView mDescriptionTV;

    private TextView mIngredientsTV;

    private ImageView mIngredientImageView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mAlsoKnownLabelTV = findViewById(R.id.also_know_label_tv);
        mAlsoKnownTV = findViewById(R.id.also_known_tv);
        mPlaceOfOriginLabelTV = findViewById(R.id.place_of_origin_label_tv);
        mOriginTV = findViewById(R.id.origin_tv);
        mDescriptionTV = findViewById(R.id.description_tv);
        mIngredientsTV = findViewById(R.id.ingredients_tv);
        mIngredientImageView = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null){
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION){
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        }
        catch (JSONException e){
            e.getStackTrace();
        }
        if (sandwich == null){
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (TextUtils.isEmpty(sandwich.getPlaceOfOrigin())){
            mOriginTV.setVisibility(View.GONE);
            mPlaceOfOriginLabelTV.setVisibility(View.GONE);
        }

        if (sandwich.getAlsoKnownAs().size() == 0){
            mAlsoKnownLabelTV.setVisibility(View.GONE);
            mAlsoKnownTV.setVisibility(View.GONE);
        }

        mOriginTV.setText(sandwich.getPlaceOfOrigin()+"\n");
        mDescriptionTV.setText(sandwich.getDescription());

        for (String ingredient : sandwich.getIngredients()){
            mIngredientsTV.append(ingredient+"\n");
        }

        for (String knownAs : sandwich.getAlsoKnownAs()){
            mAlsoKnownTV.append(knownAs+"\n");
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mIngredientImageView);

        setTitle(sandwich.getMainName());
    }
}
