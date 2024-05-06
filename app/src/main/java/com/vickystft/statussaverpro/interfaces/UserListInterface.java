package com.vickystft.statussaverpro.interfaces;


import com.vickystft.statussaverpro.model.FBStoryModel.NodeModel;
import com.vickystft.statussaverpro.model.story.TrayModel;

public interface UserListInterface {
    void userListClick(int position, TrayModel trayModel);
    void fbUserListClick(int position, NodeModel trayModel);
}
