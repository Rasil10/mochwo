package com.jnanatech.mochwo.aboutUs.view;

import com.jnanatech.mochwo.aboutUs.model.MemberModel;

import java.util.ArrayList;

public interface AboutUsView {

    void setOrganizingCommiteeMembers(ArrayList<MemberModel> organizingCommiteeMembers);
    void setScientificCommiteeMembers(ArrayList<MemberModel> scientificCommiteeMembers);

}
