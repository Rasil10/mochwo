package com.jnanatech.mochwo.aboutUs.presenter;

import android.content.Context;

import com.jnanatech.mochwo.aboutUs.model.MemberModel;
import com.jnanatech.mochwo.aboutUs.view.AboutUsView;

import java.util.ArrayList;

public class AboutUsImplementor implements AboutUsPresenter {

    AboutUsView aboutUsView;
    Context context;


    ArrayList<MemberModel> organizingCommiteeMembers = new ArrayList<>();
    ArrayList<MemberModel> scientificCommiteeMembers = new ArrayList<>();

    public AboutUsImplementor(AboutUsView aboutUsView, Context context) {
        this.aboutUsView = aboutUsView;
        this.context = context;
    }

    @Override
    public void setOrganizingCommiteeMembers() {

        organizingCommiteeMembers.add(new MemberModel("Dr Prakash K. Paudel","Executive Director , KIAS" ,"http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/Prakash_F.jpg" ));
        organizingCommiteeMembers.add(new MemberModel("Dr. Basant Giri","Scientist, KIAS" ,"http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/Basant_F.jpg" ));
        organizingCommiteeMembers.add(new MemberModel("Dr. Susma Giri","Associate Scientist, KIAS" , "http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/Susma_F.png"));
        organizingCommiteeMembers.add(new MemberModel("Dr. Madhu Sudan Kayastha","Member, KIAS" ,"http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/Madhu_F.jpg" ));
        organizingCommiteeMembers.add(new MemberModel("Dr. Hemu Kafle","Scientist, KIAS" , "http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/HEMU_FF.jpg"));

        aboutUsView.setOrganizingCommiteeMembers(organizingCommiteeMembers);

    }

    @Override
    public void setScientificCommiteeMembers() {
        scientificCommiteeMembers.add(new MemberModel("Dr. Nabin Malakar","Assistant Professor, Department of Earth Environment and Physics" ,"http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/nabin_F.png" ));
        scientificCommiteeMembers.add(new MemberModel("Dr. Vasileios Bontzorlos","Post Doc Researcher/Ecology","http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/Vasileios_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Achyut Aryal","Senior Research Fellow/Conservation Biology","http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/ACHY4T_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr Rajan Ghimire","Assistant Professor","http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/RAJAN_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Basanta Raj Adhikari","Assistant Professor/Engineering Geology","http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/BasnatRaj_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Prof. Dr. Mohan Dangi","California State University, Fresno/Solid Waste Engineering","http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/Mohan_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Olga Ameixa","Post Doctoral Fellow/Functional Biodiversity","http://conference.kias.org.np/wp-content/uploads/sites/24/2018/12/OLGA_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Keshav Basnet","Pashchimanchal Campus, Institute of Engineering","http://conference.kias.org.np/wp-content/uploads/sites/24/2019/01/Keshab-F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Bipin Kumar Acharya","Department of Medical Statistics and Epidemiology, School of Public Health","http://conference.kias.org.np/wp-content/uploads/sites/24/2019/02/Bipin_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Shalik Ram Sigdel","Institute of Tibetan Plateau Research, Chinese Academy of Sciences","http://conference.kias.org.np/wp-content/uploads/sites/24/2019/02/Shalik_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Atta-ur-Rahman","Associate Professor","http://conference.kias.org.np/wp-content/uploads/sites/24/2019/02/Atta-Ur-Rahman.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Prof. Dr. Rajib Shaw","Graduate School of Media and Governance","http://conference.kias.org.np/wp-content/uploads/sites/24/2019/02/Rajiv-Shah.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Indrajit Pal","Assistant Professor and Chair Disaster Preparedness, Mitigation and Management","http://conference.kias.org.np/wp-content/uploads/sites/24/2019/02/IndraJIt.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Ranit Chatterjee","JST Postdoctoral Fellow Graduate School of Informatics","http://conference.kias.org.np/wp-content/uploads/sites/24/2019/02/Ranjit.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Prof. Dr. Raju Sarkar","College of Science and Technology Royal University of Bhutan","http://conference.kias.org.np/wp-content/uploads/sites/24/2019/02/Raju-Sharkar.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr Ifte Ahmed","Resilience and Sustainable Development Faculty of Engineering and Built Environment","http://conference.kias.org.np/wp-content/uploads/sites/24/2019/03/Dr-Iftekhar-Ahmed.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Prof. Dr. Md. Anwarul Abedin","Department of Soil Science","http://conference.kias.org.np/wp-content/uploads/sites/24/2019/03/Md-Arwardual.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Khamarrul Azahari Razak","Department of Engineering","http://conference.kias.org.np/wp-content/uploads/sites/24/2019/03/hamarrul-Azahari-Razak.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Prof. Dr. Megha N. Parajulee","Texas A&M AgriLife Research Center","http://conference.kias.org.np/wp-content/uploads/sites/24/2019/03/Megha-Natha.jpg"));

        aboutUsView.setScientificCommiteeMembers(scientificCommiteeMembers);
    }
}
