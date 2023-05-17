package de.hdmstuttgart.einkaufsliste.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import de.hdmstuttgart.einkaufsliste.R;
import de.hdmstuttgart.einkaufsliste.models.GroceryList;
import de.hdmstuttgart.einkaufsliste.roomDB.GroceryListDatabase;


public class CreateListFragment extends Fragment {

    private GroceryListDatabase db;
    private ImageView imageView;
    private Bitmap imageBitmap; //taken picture
    private Uri imageURI; //gallery picture
    private Bitmap galleryBitmap;
    private final int GALLERY_REQ_CODE = 1000;

    public CreateListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = GroceryListDatabase.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.groceryListPicture);

        //LISTNAME - SAVE CODE
        EditText listName = view.findViewById(R.id.listNameTextCreate);
        Button saveButton = view.findViewById(R.id.saveButtonCreate);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Resources res = getResources();
                Bitmap listImage = BitmapFactory.decodeResource(res, R.drawable.list_image);

                //if no name is entered, show toast + dont save into db
                if (TextUtils.isEmpty(listName.getText().toString())) {
                    Toast.makeText(getContext(), "Please enter a name for your list", Toast.LENGTH_SHORT).show();
                }else {

                    if (imageBitmap != null) {
                        db.groceryListDAO().insertGroceryList(new GroceryList(listName.getText().toString(), imageBitmap));
                        getActivity().finish();
                    }

                    if (imageURI != null) {
                        db.groceryListDAO().insertGroceryList(new GroceryList(listName.getText().toString(), galleryBitmap));
                        getActivity().finish();
                    }

                    //if no image selected, load default picture
                    if (imageURI == null && imageBitmap == null) {
                        db.groceryListDAO().insertGroceryList(new GroceryList(listName.getText().toString(), listImage));
                        getActivity().finish();
                    }
                }

            }
        });

        //BACK BUTTON - CODE
        ImageButton backButton = view.findViewById(R.id.backButtonCreate);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        //CAM BUTTON - CODE
        //if permissionCamera granted = 0, permissionCamera not granted = -1
        int permissionCamera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        Button camButton = view.findViewById(R.id.camButton);
        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permissionCamera == 0) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(getContext(), "Please grant the permission & restart the app", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //GALLERY BUTTON - CODE
        int permissionGallery = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        Button galleryButton = view.findViewById(R.id.galleryButton);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permissionGallery == 0) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, GALLERY_REQ_CODE);
                } else {
                    Toast.makeText(getContext(), "Please grant the permission & restart the app", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void dispatchTakePictureIntent() {
        final int REQUEST_IMAGE_CAPTURE = 1;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final int REQUEST_IMAGE_CAPTURE = 1;
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
                imageURI = null;

            }
        }

        if (requestCode == GALLERY_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                if (requestCode == GALLERY_REQ_CODE) {
                    try {
                        imageURI = data.getData();
                        galleryBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageURI);
                        imageView.setImageURI(data.getData());
                        imageBitmap = null;
                    } catch (IOException e) {
                        Log.e("Error", "File not found");
                    }
                }
            }
        }
    }
}

