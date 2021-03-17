package com.maksat.uni.fragments.participants.infotabs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.maksat.uni.MainActivity;
import com.maksat.uni.R;
import com.maksat.uni.Variables;
import com.maksat.uni.bottomsheets.AddNote;
import com.maksat.uni.fragments.BaseFragment;
import com.maksat.uni.fragments.participants.ParticipantInfo;
import com.maksat.uni.interfaces.Event;
import com.maksat.uni.interfaces.Server;
import com.maksat.uni.models.ErrorBody;
import com.maksat.uni.models.ParticipantsModel;
import com.maksat.uni.models.UpdateParticipantModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends BaseFragment {
    public Profile(MainActivity mainActivity, ParticipantsModel.item item, Bitmap avatar, ParticipantInfo participantInfo) {
        this.mainActivity=mainActivity;
        this.item=item;
        this.avatar=avatar;
        this.participantInfo=participantInfo;
    }

    private MainActivity mainActivity;
    private ParticipantsModel.item item;
    private TextView id_tv, title_tv, fio_tv, status_tv, organization_tv, postition_tv, note_tv, note__tv;
    private LinearLayout note_ll;
    public ImageView avatar_iv;
    private Bitmap avatar;
    private ParticipantInfo participantInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findIDs(view);
        setInfo();

        return view;
    }

    private void findIDs(View view) {
        id_tv=view.findViewById(R.id.id_tv);
        title_tv=view.findViewById(R.id.title_tv);
        fio_tv=view.findViewById(R.id.fio_tv);
        status_tv=view.findViewById(R.id.status_tv);
        organization_tv=view.findViewById(R.id.organization_tv);
        postition_tv=view.findViewById(R.id.position_tv);
        note_ll=view.findViewById(R.id.note_ll);
        note_tv=view.findViewById(R.id.note_tv);
        note__tv=view.findViewById(R.id.note__tv);
        avatar_iv=view.findViewById(R.id.imageView2);
    }

    private void setInfo() {
        if (avatar != null) {
            avatar_iv.setImageBitmap(avatar);
        }
        String text = String.valueOf(item.getId()), noInfo="Информация отсутствует";
        id_tv.setText(text);
        title_tv.setText(item.getCategory().getNameRus());
        text = item.getLastNameRus() + " " + item.getFirstNameRus();
        fio_tv.setText(text);
        if (item.getAcrStatusStepOneId() != null) {
            switch (item.getAcrStatusStepOneId()) {
                case 2:
                    status_tv.setText("Одобрено");
                    break;
                case 3:
                    status_tv.setText("Отказано");
                    break;
                case 1:
                    status_tv.setText("На рассмотрении");
                    break;
                default:
                    status_tv.setText(noInfo);
                    break;
            }
        }else {
            status_tv.setText(noInfo);
        }
        if (item.getCompany()!=null) {
            if (item.getCompany().getNameRus() != null) {
                organization_tv.setText(item.getCompany().getNameRus());
            } else {
                organization_tv.setText(noInfo);
            }
        }else {
            organization_tv.setText(noInfo);
        }
        if (item.getCategory()!=null) {
            if (item.getCategory().getNameRus() != null) {
                postition_tv.setText(item.getCategory().getNameRus());
            } else {
                postition_tv.setText(noInfo);
            }
        }else {
            postition_tv.setText(noInfo);
        }
        if (item.getComment()!=null) {
            if (!item.getComment().equals("")) {
                note_tv.setText(item.getComment());
                note__tv.setVisibility(View.VISIBLE);
            }
        }
        note_ll.setOnClickListener(v -> {
            AddNote addNote=new AddNote(note_tv.getText().toString());
            addNote.setOnApplyClickListener(text1 -> {
                if (text1.equals("")){
                    note__tv.setVisibility(View.GONE);
                    note_tv.setText(getResources().getString(R.string.add_note));
                }else {
                    note_tv.setText(text1);
                    item.setComment(text1);
                }
                UpdateParticipantModel updateParticipantModel=new UpdateParticipantModel(item.getNeedHotel(),
                        item.getCheckInDateTime(), item.getCheckOutDateTime(), item.getHotelId(),
                        item.getRoomId(), item.getPhoneNumber(), item.getEmail(), item.getWebSite(),
                        item.getArrivalStation(), item.getArrivalDateTime(), item.getArrivalFlightInfo(),
                        item.getDepartureStation(), item.getDepartureDateTime(), item.getDepartureFlightInfo(),
                        item.getLanguageId(),item.getCategory().getId(), item.getTitleId(), item.getFirstNameRus(),
                        item.getLastNameRus(), item.getPatronymic(), item.getFirstNameEng(), item.getLastNameEng(),
                        item.getGenderId(), item.getResidenceId(), new UpdateParticipantModel.Photo("",
                        item.getPhoto(), false, null), text1, item.getSportId(),
                        item.getSportCategoryName(), item.getRegionId(), item.getCitizenshipId(),
                        item.getBirthday(), item.getPassportNumber(), item.getIssuedBy(), item.getIssuedDate(),
                        item.getValidUntilDate(), item.getBirthPlace(), item.getVisa(),
                        item.getCompany());

                Event apiUpdatePhoto= Server.GetServerWithToken(Event.class, Variables.token);
                Call<ParticipantsModel.item> call=apiUpdatePhoto.setPhoto(Variables.currentEvent.getId(), item.getId(), updateParticipantModel);
                call.enqueue(new Callback<ParticipantsModel.item>() {
                    @Override
                    public void onResponse(Call<ParticipantsModel.item> call, Response<ParticipantsModel.item> response) {
                        if (!response.isSuccessful()){
                            try {
                                if (response.errorBody() != null) {
                                    Gson gson = new Gson();
                                    ErrorBody errorBody=gson.fromJson(response.errorBody().string(), ErrorBody.class);
                                    if (errorBody!=null) {
                                        Toast.makeText(getContext(), errorBody.Ru, Toast.LENGTH_SHORT).show();
                                        Log.d("COMMENT_RESP_ERROR", "" + errorBody.Ru);
                                    }
                                }else {
                                    Log.d("COMMENT_RESP_ERROR", "ErrorBody is null.");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ParticipantsModel.item> call, Throwable t) {
                        Toast.makeText(getContext(), "Ошибка сервера.", Toast.LENGTH_SHORT).show();
                        Log.d("COMMENT_SERV_ERROR", "" + t.getMessage());
                    }
                });
                addNote.dismiss();
            });
            addNote.show(getChildFragmentManager(), "addNote");
        });
    }
}
