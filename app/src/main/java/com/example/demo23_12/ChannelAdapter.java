package com.example.demo23_12;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


/**
 * Created by zander on 30.04.17.
 */

public class ChannelAdapter extends ArrayAdapter<Channelitem> {
    Communication comm;
    TextView labelIdView;
    ImageButton btnPictureinpicture;
    ImageButton btnMainPip;
    final static private String TAG = "NewsItemAdapter";

    public ChannelAdapter(Context context, ArrayList<Channelitem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Channelitem item = getItem(position);

        if (convertView == null) {
            Log.i(TAG, "convertView == NULL: Inflating new view...");
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.channel_item, parent, false);
        }

        final View temp = convertView;

        final Button btnChannel = (Button) convertView.findViewById(R.id.btnChannel);

        final ImageButton btnPictureinpicture = (ImageButton) convertView.findViewById(R.id.btnChannelPiP);


        btnChannel.setText(item.getChannelname());

        btnChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                comm.sendCommandToTvServer("channelMain=" + item.getChannelnumber());
                Log.i(TAG, "Switching to channel: #" + item.getChannelnumber() + ": " + item.getChannelname());
                for (int i = 0; i < ChannelAdapter.this.getCount(); i++) {
                    ChannelAdapter.this.getItem(i).setIsCurrentChannel(false);

                }
                item.setIsCurrentChannel(true);
                labelIdView.setText(item.getChannelname());

            }
        });
        btnPictureinpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                comm.sendCommandToTvServer("showPip=1");
                comm.sendCommandToTvServer("channelPip=" + item.getChannelnumber());
                comm.setPiP(true);
                Log.i(TAG, "Switching to PiP channel: #" + item.getChannelnumber() + ": " + item.getChannelname());
                btnMainPip.setImageResource(R.drawable.disablepip);
            }
        });
        return convertView;
    }

    public void setLabelId(TextView newView){
        this.labelIdView = newView;
    }
    public void setBtnMainPip(ImageButton newButton) {this.btnMainPip = newButton;}
    public void setCommunication(Communication newComm) {
        this.comm = newComm;
    }


}
