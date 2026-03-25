package com.example.rizzkeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class RizzInputMethodService extends InputMethodService implements KeyboardView.OnKeyListener {
    private static final String TAG = "RizzInputMethodService";
    private KeyboardView keyboardView;
    private Keyboard keyboard;
    private ClipboardManager clipboardManager;
    private String lastClipboardText = "";

    @Override
    public void onCreate() {
        super.onCreate();
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        setupClipboardListener();
    }

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.keyboard_layout);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyListener(this);
        return keyboardView;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                keyboardView.setShifted(!keyboardView.isShifted());
                keyboardView.invalidateAllKeys();
                break;
            default:
                char code = (char) primaryCode;
                ic.commitText(String.valueOf(code), 1);
        }
    }

    @Override
    public void onPress(int primaryCode) {}

    @Override
    public void onRelease(int primaryCode) {}

    private void setupClipboardListener() {
        if (clipboardManager != null) {
            clipboardManager.addPrimaryClipChangedListener(() -> {
                String currentText = getClipboardText();
                if (!currentText.isEmpty() && !currentText.equals(lastClipboardText)) {
                    lastClipboardText = currentText;
                    showFlirtyResponseDialog(currentText);
                }
            });
        }
    }

    private String getClipboardText() {
        if (clipboardManager.hasPrimaryClip()) {
            ClipData clipData = clipboardManager.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0) {
                return clipData.getItemAt(0).getText().toString();
            }
        }
        return "";
    }

    private void showFlirtyResponseDialog(String copiedText) {
        Log.d(TAG, "Clipboard detected: " + copiedText);
        generateFlirtyResponse(copiedText);
    }

    private void generateFlirtyResponse(String text) {
        new Thread(() -> {
            String response = AiResponseGenerator.generateResponse(text);
            Log.d(TAG, "Generated response: " + response);
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}