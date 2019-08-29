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

        organizingCommiteeMembers.add(new MemberModel("Dr Prakash K. Paudel", "Convener , KIAS", "https://conference.kias.org.np/wp-content/uploads/2019/05/Prakash_F.jpg"));
        organizingCommiteeMembers.add(new MemberModel("Dr. Basant Giri", "Member, KIAS", "https://conference.kias.org.np/wp-content/uploads/2019/05/Basant_F.jpg"));
        organizingCommiteeMembers.add(new MemberModel("Dr. Susma Giri", "Member, KIAS", "https://conference.kias.org.np/wp-content/uploads/2019/05/Susma_F.jpg"));
        organizingCommiteeMembers.add(new MemberModel("Dr. Madhu Sudan Kayastha", "Member, KIAS", "https://conference.kias.org.np/wp-content/uploads/2019/05/Madhu_F.jpg"));
        organizingCommiteeMembers.add(new MemberModel("Dr. Hemu Kafle", "Member, KIAS", "https://conference.kias.org.np/wp-content/uploads/2019/05/HEMU_FF.jpg"));

        aboutUsView.setOrganizingCommiteeMembers(organizingCommiteeMembers);

    }

    @Override
    public void setScientificCommiteeMembers() {
        scientificCommiteeMembers.add(new MemberModel("Dr. Nabin Malakar", "Assistant Professor, Department of Earth Environment and Physics", "https://conference.kias.org.np/wp-content/uploads/2019/05/nabin_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Vasileios Bontzorlos", "Post Doc Researcher/Ecology", "https://conference.kias.org.np/wp-content/uploads/2019/05/Vasileios_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Achyut Aryal", "Senior Research Fellow/Conservation Biology", "https://conference.kias.org.np/wp-content/uploads/2019/05/ACHY4T_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr Rajan Ghimire", "Assistant Professor", "https://conference.kias.org.np/wp-content/uploads/2019/05/RAJAN_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Basanta Raj Adhikari", "Assistant Professor/Engineering Geology", "https://conference.kias.org.np/wp-content/uploads/2019/05/BasnatRaj_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Prof. Dr. Mohan Dangi", "California State University, Fresno/Solid Waste Engineering", "https://conference.kias.org.np/wp-content/uploads/2019/05/Mohan_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Olga Ameixa", "Post Doctoral Fellow/Functional Biodiversity", "https://conference.kias.org.np/wp-content/uploads/2019/05/OLGA_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Keshav Basnet", "Pashchimanchal Campus, Institute of Engineering", "https://conference.kias.org.np/wp-content/uploads/2019/05/Keshab-F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Bipin Kumar Acharya", "Department of Medical Statistics and Epidemiology, School of Public Health", "https://conference.kias.org.np/wp-content/uploads/2019/05/Bipin_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Shalik Ram Sigdel", "Institute of Tibetan Plateau Research, Chinese Academy of Sciences", "https://conference.kias.org.np/wp-content/uploads/2019/05/Shalik_F.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Atta-ur-Rahman", "Associate Professor", "https://conference.kias.org.np/wp-content/uploads/2019/05/Atta-Ur-Rahman.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Prof. Dr. Rajib Shaw", "Graduate School of Media and Governance", "https://conference.kias.org.np/wp-content/uploads/2019/05/Rajiv-Shah.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Indrajit Pal", "Assistant Professor and Chair Disaster Preparedness, Mitigation and Management", "https://conference.kias.org.np/wp-content/uploads/2019/05/IndraJIt.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Ranit Chatterjee", "JST Postdoctoral Fellow Graduate School of Informatics", "https://conference.kias.org.np/wp-content/uploads/2019/05/Ranjit.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Prof. Dr. Raju Sarkar", "College of Science and Technology Royal University of Bhutan", "https://conference.kias.org.np/wp-content/uploads/2019/05/Raju-Sharkar.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr Ifte Ahmed", "Resilience and Sustainable Development Faculty of Engineering and Built Environment", "https://conference.kias.org.np/wp-content/uploads/2019/05/Dr-Iftekhar-Ahmed.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Prof. Dr. Md. Anwarul Abedin", "Department of Soil Science", "https://conference.kias.org.np/wp-content/uploads/2019/05/Md-Arwardual.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Dr. Khamarrul Azahari Razak", "Department of Engineering", "https://conference.kias.org.np/wp-content/uploads/2019/05/hamarrul-Azahari-Razak.jpg"));
        scientificCommiteeMembers.add(new MemberModel("Prof. Dr. Megha N. Parajulee", "Texas A&M AgriLife Research Center", "https://conference.kias.org.np/wp-content/uploads/2019/05/Megha-Natha-796x1024.jpg"));

        aboutUsView.setScientificCommiteeMembers(scientificCommiteeMembers);
    }
}
