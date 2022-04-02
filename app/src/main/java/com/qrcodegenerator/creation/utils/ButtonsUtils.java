package com.qrcodegenerator.creation.utils;

import android.text.TextUtils;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.main.create.TypeItem;

import java.util.ArrayList;
import java.util.List;

public final class ButtonsUtils {

    private ButtonsUtils(){

    }

    private static boolean isTextEmpty(String str) {
        return !TextUtils.isEmpty(str);
    }

    public static List<TypeItem> getButtonsList(History history) {
        switch (history.type) {
            case AppConstants.TEXT:
            case AppConstants.PRODUCT:
            case AppConstants.ISBN:
                return getTextButtons();
            case AppConstants.URI:
                return getUriButtons();
            case AppConstants.TEL:
                return getTelButtons();
            case AppConstants.ADDRESSBOOK:
                return getAddressbookButtons(history);
            case AppConstants.EMAIL_ADDRESS:
                return getEmailButtons();
            case AppConstants.SMS:
                return getSmsButtons();
            case AppConstants.GEO:
                return getGeoButtons();
            case AppConstants.WIFI:
                return getWifiButtons();
            case AppConstants.CALENDAR:
                return getCalendarButtons();
            default:
                return getTextButtons();
        }
    }

    public static List<TypeItem> getCreatedButtons() {
        List<TypeItem> buttons = new ArrayList<>();
        buttons.add(new TypeItem(R.drawable.ic_save, R.string.save, R.color.buttonSix));
        buttons.add(new TypeItem(R.drawable.ic_share_white, R.string.share, R.color.buttonTen));
        buttons.add(new TypeItem(R.drawable.ic_edit, R.string.edit, R.color.buttonEight));
        buttons.add(new TypeItem(R.drawable.ic_delete, R.string.delete, R.color.buttonNine));
        return buttons;
    }

    private static List<TypeItem> getTextButtons() {
        List<TypeItem> buttons = new ArrayList<>();
        buttons.add(new TypeItem(R.drawable.ic_search, R.string.btn_title_search , R.color.buttonNine));
        buttons.add(new TypeItem(R.drawable.ic_share_white, R.string.btn_title_share , R.color.buttonTen));
        buttons.add(new TypeItem(R.drawable.ic_content_copy, R.string.btn_title_copy , R.color.buttonEleven));
        return buttons;
    }

    private static List<TypeItem> getUriButtons() {
        List<TypeItem> buttons = new ArrayList<>();
        buttons.add(new TypeItem(R.drawable.website, R.string.btn_title_open_url , R.color.buttonSix));
        buttons.add(new TypeItem(R.drawable.ic_share_white, R.string.btn_title_share , R.color.buttonTen));
        buttons.add(new TypeItem(R.drawable.ic_content_copy, R.string.btn_title_copy , R.color.buttonEleven));
        return buttons;
    }

    private static List<TypeItem> getTelButtons() {
        List<TypeItem> buttons = new ArrayList<>();
        buttons.add(new TypeItem(R.drawable.add_contact, R.string.btn_title_add_contact , R.color.buttonTwo));
        buttons.add(new TypeItem(R.drawable.ic_call_made, R.string.btn_title_dial_phone , R.color.buttonThree));
        buttons.add(new TypeItem(R.drawable.phone_message, R.string.btn_title_send_sms , R.color.buttonFour));
        buttons.add(new TypeItem(R.drawable.ic_share_white, R.string.btn_title_share , R.color.buttonTen));
        buttons.add(new TypeItem(R.drawable.ic_content_copy, R.string.btn_title_copy , R.color.buttonEleven));
        return buttons;
    }

    private static List<TypeItem> getAddressbookButtons(History history) {
        List<TypeItem> buttons = new ArrayList<>();
        buttons.add(new TypeItem(R.drawable.add_contact, R.string.btn_title_add_contact , R.color.buttonTwo));
        if (isTextEmpty(history.phone) || isTextEmpty(history.phoneTwo)) {
            buttons.add(new TypeItem(R.drawable.ic_call_made, R.string.btn_title_dial_phone , R.color.buttonThree));
            buttons.add(new TypeItem(R.drawable.phone_message, R.string.btn_title_send_sms , R.color.buttonFour));
        }
        if (isTextEmpty(history.email)) {
            buttons.add(new TypeItem(R.drawable.email_send, R.string.btn_title_send_email , R.color.buttonFive));
        }
        if (isTextEmpty(history.uri)) {
            buttons.add(new TypeItem(R.drawable.website, R.string.btn_title_open_url , R.color.buttonSix));
        }
        buttons.add(new TypeItem(R.drawable.ic_share_white, R.string.btn_title_share , R.color.buttonTen));
        buttons.add(new TypeItem(R.drawable.ic_content_copy, R.string.btn_title_copy , R.color.buttonEleven));
        return buttons;
    }

    private static List<TypeItem> getEmailButtons() {
        List<TypeItem> buttons = new ArrayList<>();
        buttons.add(new TypeItem(R.drawable.email_send, R.string.btn_title_send_email , R.color.buttonFive));
        buttons.add(new TypeItem(R.drawable.ic_share_white, R.string.btn_title_share , R.color.buttonTen));
        buttons.add(new TypeItem(R.drawable.ic_content_copy, R.string.btn_title_copy , R.color.buttonEleven));
        return buttons;
    }

    private static List<TypeItem> getSmsButtons() {
        List<TypeItem> buttons = new ArrayList<>();
        buttons.add(new TypeItem(R.drawable.phone_message, R.string.btn_title_send_sms , R.color.buttonFour));
        buttons.add(new TypeItem(R.drawable.ic_call_made, R.string.btn_title_dial_phone , R.color.buttonThree));
        buttons.add(new TypeItem(R.drawable.ic_share_white, R.string.btn_title_share , R.color.buttonTen));
        buttons.add(new TypeItem(R.drawable.ic_content_copy, R.string.btn_title_copy , R.color.buttonEleven));
        return buttons;
    }

    private static List<TypeItem> getGeoButtons() {
        List<TypeItem> buttons = new ArrayList<>();
        buttons.add(new TypeItem(R.drawable.ic_map, R.string.btn_title_open_map , R.color.buttonOne));
        buttons.add(new TypeItem(R.drawable.ic_share_white, R.string.btn_title_share , R.color.buttonTen));
        buttons.add(new TypeItem(R.drawable.ic_content_copy, R.string.btn_title_copy , R.color.buttonEleven));
        return buttons;
    }

    private static List<TypeItem> getWifiButtons() {
        List<TypeItem> buttons = new ArrayList<>();
        buttons.add(new TypeItem(R.drawable.wifi, R.string.btn_title_connect_wifi , R.color.buttonSeven));
        buttons.add(new TypeItem(R.drawable.ic_share_white, R.string.btn_title_share , R.color.buttonTen));
        buttons.add(new TypeItem(R.drawable.ic_content_copy, R.string.btn_title_copy , R.color.buttonEleven));
        return buttons;
    }

    private static List<TypeItem> getCalendarButtons() {
        List<TypeItem> buttons = new ArrayList<>();
        buttons.add(new TypeItem(R.drawable.calendar_plus, R.string.btn_title_add_event , R.color.buttonEight));
        buttons.add(new TypeItem(R.drawable.ic_share_white, R.string.btn_title_share , R.color.buttonTen));
        buttons.add(new TypeItem(R.drawable.ic_content_copy, R.string.btn_title_copy , R.color.buttonEleven));
        return buttons;
    }
}
