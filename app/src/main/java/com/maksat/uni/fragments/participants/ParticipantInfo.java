package com.maksat.uni.fragments.participants;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.maksat.uni.BuildConfig;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.adapters.ParticipantVPAdapter;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.fragments.participants.infotabs.Badge;
import com.maksat.uni.fragments.participants.infotabs.Profile;
import com.maksat.uni.fragments.participants.infotabs.Schedule;
import com.maksat.uni.interfaces.Event;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.ErrorBody;
import com.maksat.uni.models.ParticipantsModel;
import com.maksat.uni.models.UpdateParticipantModel;
import com.maksat.uni.models.Zone;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticipantInfo extends BaseFragment {


    public ParticipantInfo(MainActivity mainActivity, ParticipantsModel.item item, List<Zone> zones){
        this.mainActivity=mainActivity;
        this.item=item;
        this.zones=zones;
    }


    private final MainActivity mainActivity;
    private TabLayout tabLayout;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;
    private ParticipantVPAdapter participantVPAdapter;
    private ArrayList<Fragment> arrayFragments;
    private ArrayList<String> arrayTitles;
    private final ParticipantsModel.item item;
    private MaterialButton close_btn, photo_btn;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton search_fab;
    private final List<Zone> zones;
    private int avatarFlag=0;
    public TextView title_tv;

    private String cameraFilePath;
    private File image;
    private String photopath;
    private Bitmap avatar;

    private static final int REQUEST = 112;
    private static final int GALLERY_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @SuppressLint("StaticFieldLeak")

        protected Bitmap doInBackground(String... urls) {
            Bitmap mIcon11 = null;
            try {
                String url=Variables.ip+item.getPhoto();
                InputStream in = new java.net.URL(url).openStream();
                avatar = BitmapFactory.decodeStream(in);
                avatarFlag=1;
                if (viewPager!=null){
                    mainActivity.runOnUiThread(() -> ((Profile)participantVPAdapter.getItem(0)).avatar_iv.setImageBitmap(avatar));
                }
            } catch (Exception e) {
                Log.e("Error", Objects.requireNonNull(e.getMessage()));
                e.printStackTrace();
            }
            return mIcon11;
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_participant_info, container, false);
        findIDs(view);
        if (item.getPhoto()!=null) {
            if (!item.getPhoto().equals(""))
            new DownloadImageTask().execute();
        }
        setFragments();
        setTitles();
        setViewPagerTabLayout();
        return view;
    }

    private void findIDs(@NonNull View view) {
        fragmentManager=getChildFragmentManager();
        tabLayout=view.findViewById(R.id.tabs);
        viewPager=view.findViewById(R.id.participant_vp);
        close_btn=view.findViewById(R.id.close_btn);
        bottomAppBar=view.findViewById(R.id.bottomAppBar2);
        search_fab=view.findViewById(R.id.schedule_search_fab);
        photo_btn=view.findViewById(R.id.photo_btn);
        title_tv=view.findViewById(R.id.title_tv);
        MaterialButton info_btn = view.findViewById(R.id.info_btn);
        photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureFromCamera();
            }
        });
        info_btn.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mainActivity);
            bottomSheetDialog.setContentView(R.layout.lo_status_info);
            bottomSheetDialog.show();
        });
        close_btn.setOnClickListener(v -> {
            Fragment fragment = mainActivity.fragmentManager.findFragmentByTag("participantInfo");
            if (fragment != null) {
                mainActivity.fragmentManager.beginTransaction().hide(fragment).commit();
                //beginTransaction();
                mainActivity.fragmentManager.beginTransaction().remove(fragment).commit();

            } //beginTransaction();
            mainActivity.bottomAppBar.setVisibility(View.VISIBLE);

        });

        search_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=mainActivity.fragmentManager.findFragmentByTag("scheduleSearch");
                if(fragment!=null){
                    mainActivity.fragmentManager.beginTransaction().show(fragment).commit();
                }else {
                    mainActivity.fragmentManager.beginTransaction().add(R.id.container, new ScheduleSearch(mainActivity, item), "scheduleSearch").commit();
                }
            }
        });
    }

    private void captureFromCamera() {
        String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (hasPermissions(getContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions((android.app.Activity) getContext(), PERMISSIONS, REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }



    private boolean hasPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }

    private File createImageFile() {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);

        } catch (IOException e) {
            e.printStackTrace();
        }

        cameraFilePath = String.valueOf(Uri.fromFile(image));
        cameraFilePath=cameraFilePath.replaceAll("file:///", "");
        return image;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == android.app.Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    Uri selectedGaleryImage = data.getData();
                    String path = getPathFromURI(selectedGaleryImage);
                    File galleryFile = new File(path);
                    //galleryFile=compressImage(galleryFile, path);
                    //uploadToServer(galleryFile);
                    break;
                case CAMERA_REQUEST_CODE:

                    File cameraFile = new File(cameraFilePath);
                    cameraFile = checkIfRotated(cameraFile);
                    if (cameraFile != null) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        avatar = BitmapFactory.decodeFile(cameraFile.getPath(), options);
                        if (avatar != null) {
                            if (avatar.getWidth() >= avatar.getHeight()) {
                                avatar = Bitmap.createBitmap(avatar, avatar.getWidth() / 2 - avatar.getHeight() / 2, 0, avatar.getHeight(), avatar.getHeight());
                            } else {
                                avatar = Bitmap.createBitmap(avatar, 0, avatar.getHeight() / 2 - avatar.getWidth() / 2, avatar.getWidth(), avatar.getWidth());
                            }
                            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mainActivity);
                            bottomSheetDialog.setContentView(R.layout.dialog_accept_iv);
                            ImageView picture = bottomSheetDialog.findViewById(R.id.imageView13);
                            MaterialButton accept_btn = bottomSheetDialog.findViewById(R.id.allow_btn);
                            MaterialButton deny_btn = bottomSheetDialog.findViewById(R.id.deny_btn);
                            if (picture != null) {
                                picture.setImageBitmap(avatar);
                            }
                            File finalCameraFile = cameraFile;
                            if (accept_btn != null) {
                                accept_btn.setOnClickListener(v -> {
                                    uploadImage(avatar, finalCameraFile);
                                    avatarFlag = 1;
                                    ((Profile) participantVPAdapter.getItem(0)).avatar_iv.setImageBitmap(avatar);
                                    bottomSheetDialog.dismiss();
                                });
                            }
                            if (deny_btn != null) {
                                deny_btn.setOnClickListener(v -> bottomSheetDialog.dismiss());
                            }
                            bottomSheetDialog.show();
                            //viewPager.getAdapter().notifyDataSetChanged();
                        }

                        //uploadImage(cameraFile);
                    }
                    /*cameraFile=compressImage(cameraFile, cameraFilePath);
                    uploadToServer(cameraFile);*/
                    break;
            }
        }
    }

    private @org.jetbrains.annotations.Nullable File checkIfRotated(File file) {
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            FileInputStream inputStream = new FileInputStream(file);
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();
            final int REQUIRED_SIZE = 75;
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();
            ExifInterface exifInterface=null;
            try {
                exifInterface=new ExifInterface(cameraFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert exifInterface != null;
            int orientation=exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Matrix matrix=new Matrix();
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(180);
                    break;
                default:
            }
            assert selectedBitmap != null;
            Bitmap rotatedBitmap=Bitmap.createBitmap(selectedBitmap, 0, 0, selectedBitmap.getWidth(), selectedBitmap.getHeight(), matrix, true);
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            return file;
        } catch (Exception e) {
            Log.d("Не удалось сжать", Objects.requireNonNull(e.getMessage()));
            return null;
        }
    }

    private void uploadImage(Bitmap cameraFile, File finalCameraFile) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cameraFile.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

        UpdateParticipantModel updateParticipantModel=new UpdateParticipantModel(item.getNeedHotel(), item.getCheckInDateTime(), item.getCheckOutDateTime(), item.getHotelId(), item.getRoomId(),
                item.getPhoneNumber(), item.getEmail(), item.getWebSite(), item.getArrivalStation(), item.getArrivalDateTime(), item.getArrivalFlightInfo(), item.getDepartureStation(), item.getDepartureDateTime(),
                item.getDepartureFlightInfo(), item.getLanguageId(),item.getCategory().getId(), item.getTitleId(), item.getFirstNameRus(), item.getLastNameRus(), item.getPatronymic(), item.getFirstNameEng(),
                item.getLastNameEng(), item.getGenderId(), item.getResidenceId(), new UpdateParticipantModel.Photo(finalCameraFile.getName(), null, true, encodedImage), item.getComment(),
                item.getSportId(), item.getSportCategoryName(), item.getRegionId(), item.getCitizenshipId(),item.getBirthday(), item.getPassportNumber(), item.getIssuedBy(), item.getIssuedDate(), item.getValidUntilDate(), item.getBirthPlace(), item.getVisa(),
                item.getCompany());

        Event apiUpdatePhoto= Server.GetServerWithToken(Event.class, Variables.token);
        Call<ParticipantsModel.item> call=apiUpdatePhoto.setPhoto(Variables.currentEvent.getId(), item.getId(), updateParticipantModel);
        call.enqueue(new Callback<ParticipantsModel.item>() {
            @Override
            public void onResponse(Call<ParticipantsModel.item> call, Response<ParticipantsModel.item> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        if(mainActivity.fragmentManager.findFragmentByTag("three")!=null){
                            if(((ParticipantsFragment) Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("three"))).items!=null) {
                                for (int i = 0; i < ((ParticipantsFragment) Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("three"))).items.size(); i++) {
                                    if (item == ((ParticipantsFragment) Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("three"))).items.get(i)) {
                                        ((ParticipantsFragment) Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("three"))).items.get(i).setPhoto(response.body().getPhoto());
                                        Log.d("TAG", "onResponse: ");
                                        break;
                                    }
                                }
                            }
                        }
                        Toast.makeText(getContext(), "Изображение успешно загружено!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    try {
                        if (response.errorBody() != null) {
                            Gson gson = new Gson();
                            ErrorBody errorBody=gson.fromJson(response.errorBody().string(), ErrorBody.class);
                            Toast.makeText(getContext(), errorBody.Ru, Toast.LENGTH_SHORT).show();
                            Log.d("PHOTO_RESP_ERROR", ""+errorBody.Ru);
                        }else {
                            Log.d("PHOTO_RESP_ERROR", "ErrorBody is null.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ParticipantsModel.item> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                Log.d("PHOTO_SERV_ERROR", ""+t.getMessage());
            }
        });

    }

    private String getPathFromURI(Uri selectedGaleryImage) {

        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireActivity().getContentResolver().query(selectedGaleryImage, proj, null, null, null);
        assert cursor != null;
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;

    }


    private void setFragments() {
        arrayFragments=new ArrayList<>();
        arrayFragments.add(new Profile(mainActivity, item, avatar, this));
        arrayFragments.add(new Badge(mainActivity, item, zones, this));
        arrayFragments.add(new Schedule(mainActivity, item, this));
    }

    private void setTitles() {
        arrayTitles=new ArrayList<>();
        arrayTitles.add("Профиль");
        arrayTitles.add("Бейдж");
        arrayTitles.add("Расписание");
    }

    private void setViewPagerTabLayout() {
        participantVPAdapter = new ParticipantVPAdapter(getChildFragmentManager(), arrayFragments, arrayTitles);
        viewPager.setAdapter(participantVPAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position==0){
                    title_tv.setText("");
                }else {
                    String text=item.getLastNameRus()+" "+item.getFirstNameRus();
                    title_tv.setText(text);
                }
                if (avatar != null && avatarFlag==1) {
                    ((Profile)participantVPAdapter.getItem(0)).avatar_iv.setImageBitmap(avatar);
                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                super.onTabSelected(tab);
                if (tab.getPosition()!=0){
                    photo_btn.setVisibility(View.GONE);
                }else {
                    photo_btn.setVisibility(View.VISIBLE);
                }
                if (tab.getPosition()==2){
                    bottomAppBar.setVisibility(View.VISIBLE);
                    search_fab.show();
                }else {
                    search_fab.hide();
                    bottomAppBar.setVisibility(View.GONE);
                }
            }
        });
        //viewPager.setCurrentItem(1);
    }

    private void beginTransaction(){
        switch (Variables.fragment) {
            case "two":
                mainActivity.fragmentManager.beginTransaction().show(Objects.requireNonNull(mainActivity.fragmentManager.findFragmentByTag("statisticParticipants"))).commit();
                break;
            case "three":
                mainActivity.ChangeScreen("three");
                break;
        }
    }

}
