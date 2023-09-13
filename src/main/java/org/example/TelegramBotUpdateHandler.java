package org.example;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Currency;
import java.util.ResourceBundle;
import java.util.StringJoiner;

public class TelegramBotUpdateHandler {
    private TelegramBot bot = new TelegramBot(ResourceBundle.getBundle("settings").getString("bot.token"));

    public String generatePrettyCurrencyInfo(CurrencyAndInfo currencyAndInfo,String flag) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
        Class<?> clazz = CurrencyAndInfo.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields){
            field.setAccessible(true);
            String fieldName = field.getName();
            stringBuilder.append(flag+fieldName+":").append(field.get(currencyAndInfo)).append("\n");
        }
        return stringBuilder.toString();
    }

    public void handle(Update update) throws IOException, InterruptedException, IllegalAccessException {
        Message message = update.message();
        CallbackQuery callbackQuery = update.callbackQuery();
        String text = message.text();


        if(message!=null){
            Long chatId = message.chat().id();
            if(text.equals("/start")){
                SendMessage sendMessage = new SendMessage(chatId, "Welcome to Currency bot\uD83D\uDCB4!");
                KeyboardButton[] buttons = new KeyboardButton[2];
                KeyboardButton button1 = new KeyboardButton("\uD83C\uDDF7\uD83C\uDDFARubl");
                buttons[0] = button1;
                KeyboardButton button2 = new KeyboardButton("\uD83C\uDDFA\uD83C\uDDF8Dollar");
                buttons[1] = button2;
                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(buttons);
                replyKeyboardMarkup.resizeKeyboard(true);
                sendMessage.replyMarkup(replyKeyboardMarkup);
                bot.execute(sendMessage);
            }
            else if(text.equals("\uD83C\uDDFA\uD83C\uDDF8Dollar")){
                System.out.println("Dollar working!");
                CurrencyAndInfo currencyAndInfo = new CurrencyAndInfo();
                CurrencyAndInfo currencyAndInfo1 = currencyAndInfo.generateCurrency("https://cbu.uz/oz/arkhiv-kursov-valyut/json/USD/2023-01-01/");
                String info = generatePrettyCurrencyInfo(currencyAndInfo1,"\uD83C\uDDFA\uD83C\uDDF8");
                System.out.println(info);
                SendMessage sendMessage = new SendMessage(chatId,info);
                bot.execute(sendMessage);
                bot.execute(new DeleteMessage(chatId,message.messageId()));


            }
            else if(text.equals("\uD83C\uDDF7\uD83C\uDDFARubl")){
                System.out.println("Rubl working!");
                CurrencyAndInfo currencyAndInfo = new CurrencyAndInfo();
                CurrencyAndInfo currencyAndInfo1 = currencyAndInfo.generateCurrency("https://cbu.uz/oz/arkhiv-kursov-valyut/json/RUB/2023-01-01/");
                String info = generatePrettyCurrencyInfo(currencyAndInfo1,"\uD83C\uDDF7\uD83C\uDDFA");
                System.out.println(info);
                SendMessage sendMessage = new SendMessage(chatId,info);
                bot.execute(sendMessage);
                bot.execute(new DeleteMessage(chatId,message.messageId()));
            }
            else{
                DeleteMessage deleteMessage = new DeleteMessage(chatId, message.messageId());
                bot.execute(deleteMessage);            }

        }
    }
}
