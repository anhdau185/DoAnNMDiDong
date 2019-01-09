package com.example.loanmanagementapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.loanmanagementapp.adapter.HelpAdapter;
import com.example.loanmanagementapp.model.Help;

import java.util.ArrayList;
import java.util.List;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_help);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView rv = findViewById(R.id.rv_help);
        HelpAdapter adapter = new HelpAdapter(this, HelpInfo.getHelpList());
        adapter.setItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpAdapter.HelpViewHolder holder = (HelpAdapter.HelpViewHolder) v.getTag();
                Help help = holder.getHelpObject();
                // do something with this Help object
                makeOkDialog(HelpActivity.this, help.getTitle(), help.getContent()).show();
            }
        });
        adapter.setItemLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    private AlertDialog makeOkDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    static class HelpInfo {
        static final String helpTitle00 = "Thêm một khoản nợ mới";
        static final String helpContent00 = "Để thêm một khoản nợ mới, ấn nút 'Thêm nợ mới' (dấu +) ở màn hình chính hoặc nút '+' hình tròn màu hồng ở danh sách nợ. " +
                "Điền thông tin và bấm dấu tick để lưu khoản nợ này hoặc dấu 'x' để hủy thêm khoản nợ.\n" +
                "* Lưu ý: \n" +
                "- Trường Tên và Số tiền nợ không được bỏ trống." +
                "\n- Trường Lãi suất bỏ trống sẽ tự hiểu là lãi suất 0%/năm." +
                "\n- Các trường Tên, Số tiền nợ, Lãi suất và Ngày vay không thể chỉnh sửa sau khi đã lưu. Hãy cân nhắc kỹ trước khi lưu.";

        static final String helpTitle01 = "Chỉnh sửa một khoản nợ";
        static final String helpContent01 = "Để chỉnh sửa một khoản nợ: đè chọn khoản nợ ở danh sách nợ và chọn 'Chỉnh sửa' trong popup menu; " +
                "hoặc chọn khoản nợ rồi ấn nút 'Sửa thông tin' (biểu tượng cây bút ở góc trên bên phải màn hình).\n" +
                "* Lưu ý: Bạn chỉ có thể sửa các trường Điện thoại, Địa chỉ và Mô tả.";

        static final String helpTitle02 = "Xóa một khoản nợ";
        static final String helpContent02 = "Để xóa một khoản nợ: đè chọn khoản nợ ở danh sách nợ và chọn 'Xóa' trong popup menu; " +
                "hoặc chọn khoản nợ rồi ấn nút 'Xóa' (biểu tượng thùng rác ở góc trên bên phải màn hình). Nhấn 'Đồng ý' để xác nhận xóa khoản nợ.\n" +
                "* Lưu ý: Khoản nợ sẽ bị xóa hoàn toàn và không thể khôi phục.";

        static final String helpTitle03 = "Thêm nợ cho một người vay";
        static final String helpContent03 = "Để thêm nợ cho một người vay, chọn khoản nợ ở danh sách nợ rồi ấn nút 'THÊM NỢ' ở góc dưới bên trái màn hình. " +
                "Nhập số tiền nợ bạn muốn thêm và ấn 'Lưu'.\n" +
                "* Lưu ý: Đối với những khoản nợ có lãi suất, điều kiện để được thêm nợ là đã trả tiền lãi (tiền lãi hiện tại phải bằng 0).";

        static final String helpTitle04 = "Trừ nợ cho một người vay";
        static final String helpContent04 = "Để trừ nợ cho một người vay, chọn khoản nợ ở danh sách nợ rồi ấn nút 'TRỪ NỢ' ở dưới cùng màn hình.\n" +
                "- Đối với khoản nợ không có lãi suất: xóa hết nợ cho người vay này.\n" +
                "- Đối với khoản nợ có lãi suất, có 2 trường hợp:\n" +
                "\t+ Trừ tiền lãi: xóa tiền lãi cho người vay và giữ lại tiền nợ gốc.\n" +
                "\t+ Hoàn tất khoản nợ: xóa cả tiền nợ gốc lẫn tiền lãi cho người vay (người vay hết nợ).\n" +
                "Người vay đã hoàn tất khoản nợ sẽ không bị xóa mà vẫn còn đó. Bạn có thể thêm nợ lại cho người đó sau này.";

        static final String helpTitle05 = "Liên lạc với người vay";
        static final String helpContent05 = "Để liên lạc với người vay, chọn khoản nợ ở danh sách nợ rồi ấn nút 'LIÊN LẠC' ở góc dưới bên phải màn hình " +
                "và chọn gọi điện thoại hoặc nhắn tin cho người vay này.\n" +
                "Nếu bạn chưa thêm số điện thoại của người vay này, bạn có thể ấn nút 'Sửa thông tin' (biểu tượng cây bút ở góc trên bên phải màn hình) " +
                "và điền số điện thoại vào trường Điện thoại hoặc ấn nút 'Danh bạ' để chọn số điện thoại từ danh bạ trong máy bạn.";

        static final String helpTitle06 = "Cách tính lãi của app như thế nào?";
        static final String helpContent06 = "Tiền lãi được cập nhật từng ngày theo công thức:\n" +
                "[Số tiền nợ] x [Lãi suất (%/ngày)] x [Số ngày chưa trả lãi] ('x' là phép nhân).\n" +
                "Trong đó:\n" +
                "- [Số tiền nợ] là số tiền nợ gốc của người vay.\n" +
                "- [Lãi suất (%/ngày)] bằng lãi suất (%/năm) chia cho 365.\n" +
                "- [Số ngày chưa trả lãi] là số ngày giữa lần cuối trả lãi và ngày hiện tại.\n" +
                "Những người vay chưa trả lãi từ 1 tháng trở lên sẽ được hiển thị ở màn hình chính.";

        static List<Help> getHelpList() {
            List<Help> helpList = new ArrayList<>();
            helpList.add(new Help(helpTitle00, helpContent00));
            helpList.add(new Help(helpTitle01, helpContent01));
            helpList.add(new Help(helpTitle02, helpContent02));
            helpList.add(new Help(helpTitle03, helpContent03));
            helpList.add(new Help(helpTitle04, helpContent04));
            helpList.add(new Help(helpTitle05, helpContent05));
            helpList.add(new Help(helpTitle06, helpContent06));
            return helpList;
        }
    }
}
