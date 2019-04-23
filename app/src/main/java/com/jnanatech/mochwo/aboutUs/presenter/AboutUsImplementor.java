package com.jnanatech.mochwo.aboutUs.presenter;

import android.content.Context;

import com.jnanatech.mochwo.aboutUs.model.MemberModel;
import com.jnanatech.mochwo.aboutUs.view.AboutUsView;

import java.util.ArrayList;

public class AboutUsImplementor implements AboutUsPresenter {

    AboutUsView aboutUsView;
    Context context;


    ArrayList<MemberModel> memberModels = new ArrayList<>();

    public AboutUsImplementor(AboutUsView aboutUsView, Context context) {
        this.aboutUsView = aboutUsView;
        this.context = context;
    }

    @Override
    public void setMembers() {

        memberModels.add(new MemberModel("Dr Prakash K. Paudel","Executive Director , KIAS" ,"http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/Prakash_F.jpg" ));
        memberModels.add(new MemberModel("Dr. Basant Giri","Scientist, KIAS" ,"http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/Basant_F.jpg" ));
        memberModels.add(new MemberModel("Dr. Susma Giri","Associate Scientist, KIAS" , "http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/Susma_F.png"));
        memberModels.add(new MemberModel("Dr. Madhu Sudan Kayastha","Member, KIAS" ,"http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/Madhu_F.jpg" ));
        memberModels.add(new MemberModel("Dr. Hemu Kafle","Scientist, KIAS" , "http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/HEMU_FF.jpg"));

        aboutUsView.getMembers(memberModels);

    }
}
