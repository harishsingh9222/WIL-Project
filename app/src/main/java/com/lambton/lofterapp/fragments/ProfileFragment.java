package com.lambton.lofterapp.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lambton.lofterapp.BuildConfig;
import com.lambton.lofterapp.R;
import com.lambton.lofterapp.database.DatabaseHelper;
import com.lambton.lofterapp.models.user.LoginUserProfile;
import com.lambton.lofterapp.utils.CircleTransform;
import com.lambton.lofterapp.utils.CommonMethods;
import com.lambton.lofterapp.utils.MyLocationListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.lambton.lofterapp.utils.Utility.REQUEST_CAMERA;
import static com.lambton.lofterapp.utils.Utility.REQUEST_MULTIPLE_PERMISSIONS;
import static com.lambton.lofterapp.utils.Utility.SELECT_FILE;
import static com.lambton.lofterapp.utils.Utility.cameraFilePath;

/**
 * Create this class to display Contact Us
 * functionality of this Golf Club.
 */
public class ProfileFragment extends Fragment {
    private static final String DIR_NAME = "LofterApp";

    /*********************************
     * INSTANCES OF CLASSES
     *******************************/
    View viewRootFragment;
    ImageView imgProfile;
    LinearLayout llChangeImg;
    EditText etFname, etLname, etEmail, etContact;
    Button btnUpdate;

    DatabaseHelper databaseHelper;
    LoginUserProfile userProfile;

    String strProfileImageURL;
    String strFirstName, strLastName, strEmail, strContact;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRootFragment = inflater.inflate(R.layout.fragment_profile, container, false);

        imgProfile = viewRootFragment.findViewById(R.id.img_profile);
        etFname = viewRootFragment.findViewById(R.id.et_fname);
        etLname = viewRootFragment.findViewById(R.id.et_lname);
        etEmail = viewRootFragment.findViewById(R.id.et_email);
        etContact = viewRootFragment.findViewById(R.id.et_contact);
        llChangeImg = viewRootFragment.findViewById(R.id.ll_change_img);
        btnUpdate = viewRootFragment.findViewById(R.id.btn_update);

        llChangeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

        updateUI();
        return viewRootFragment;
    }

    private void updateUserProfile() {
        strFirstName = etFname.getText().toString().trim();
        strLastName = etLname.getText().toString().trim();
        strEmail = etEmail.getText().toString().trim();
        strContact = etContact.getText().toString().trim();

        if (strFirstName.length() > 0 && strLastName.length() > 0 && strProfileImageURL.length() > 0) {
            userProfile.setImgPath(strProfileImageURL);
            userProfile.setFirstName(strFirstName);
            userProfile.setLastName(strLastName);
            userProfile.setEmail(strEmail);
            userProfile.setContact(strContact);
            if (databaseHelper.update(userProfile)) {
                CommonMethods.alertMessageOk(getActivity(), "SUCCESS",
                        "Profile updated successfully.");
            }else{
                CommonMethods.alertMessageOk(getActivity(), "ERROR",
                        "Unable to update profile. Please try again.");
            }
        } else {
            CommonMethods.alertMessageOk(getActivity(), "Error",
                    "First name and Last name could not be empty.");
        }
    }

    private void updateUI() {
        databaseHelper = new DatabaseHelper(getActivity());
        userProfile = databaseHelper.getLoginUser();
        updateProfileImg(Uri.parse(userProfile.getImgPath()));
        etFname.setText(userProfile.getFirstName());
        etLname.setText(userProfile.getLastName());
        etEmail.setText(userProfile.getEmail());
        etContact.setText(userProfile.getContact());

        etFname.postDelayed(new Runnable() {
            @Override
            public void run() {
                etFname.setSelection(etFname.length());
            }
        }, 500);
    }

    private void selectImage() {
        final CharSequence[] items = {"Take a picture", "Choose from gallery",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (checkAndRequestPermissions()) {
                    if (items[item].equals("Take a picture")) {
                        try {
                            cameraIntent();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (items[item].equals("Choose from gallery")) {
                        galleryIntent();
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(),
                BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
        getActivity().startActivityForResult(intent, REQUEST_CAMERA);
    }

    private File createImageFile() throws IOException {

        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File mFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM) + File.separator + getString(R.string.app_name));
        File image = new File(mFolder.getAbsolutePath() + "/" + imageFileName + "_.jpg");

        if (!mFolder.exists()) {
            mFolder.mkdir();
        }

        cameraFilePath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void galleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Objects.requireNonNull(getActivity()).startActivityForResult(pickPhoto, SELECT_FILE);
    }

    public void updateProfileImg(Uri imageURL) {
        strProfileImageURL = imageURL.toString();
        Glide.with(this).load(imageURL)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(getActivity()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
    }

    public void proceedAfterPermission() {
        imgProfile.performClick();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean checkAndRequestPermissions() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);

        int storagePermission = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        int writeStoragePermission = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(),
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()])
                    , REQUEST_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }
}