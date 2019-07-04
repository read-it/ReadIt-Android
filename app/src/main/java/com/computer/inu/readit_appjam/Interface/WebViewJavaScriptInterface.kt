package com.computer.inu.readit_appjam.Interface

import android.webkit.JavascriptInterface

interface WebViewJavaScriptInterface {
    fun WebViewJavaScriptInterface(){
    }
}

@JavascriptInterface
fun highlight() : String =
    "javascript:" +
            "function highlightSelection(color){" +
            "var sel = window.getSelection(); " +
            "var range = sel.getRangeAt(0); " +
            "var selectedText = range.extractContents();" +
            "if(selectedText == null)" +
            "return;" +
            "var newNode = document.createElement('span'); " +
            "if(color == 1)" +
            "newNode.setAttribute('style','background-color:#b2b2b2'); " +
            "else if(color == 2)"+
            "newNode.setAttribute('style','background-color:#ffc570'); " +
            "else if(color == 3)"+
            "newNode.setAttribute('style','background-color:#ffffff'); " +
            "newNode.appendChild(selectedText);" +
            "range.insertNode(newNode);}" +
            "function show_alert(){" +
            "window.alert(range.startOffset.toString() + range.endOffset.toString());}"

/*
    "javascript:" +
            "function highlightSelection(i, color){"+
            "var sel = window.getSelection(); " +
            "var range = sel.getRangeAt(0); " +
            "var selectedText = range.extractContents();" +
            "var newNode = document.createElement('span'); " +
            "var tmp_str = String(i);"+
            "var cName = 'hl'+tmp_str;"+
            "newNode.className = cName;"+
            "newNode.appendChild(selectedText);" +
            "range.insertNode(newNode);"+
            "var mark = document.getElementsByClassName(cName);" +
            "if(color == 1){" +
            "window.alert(cName);"+
            "var j = 0; for(j = 0;j<mark.length;j++){mark[j].style.backgroundColor = '#b2b2b2';}}" +
            "else if(color == 2){"+
            "var j = 0; for(j = 0;j<mark.length;j++){mark[j].style.backgroundColor = '#ffc570';}}" +
            "else if(color == 3){"+
            "var j = 0; for(j = 0;j<mark.length;j++){mark[j].style.backgroundColor = '#ffffff';}}" +
            //"var rng = document.createRange();" +
            //"rng.selectNodeContents(document.getElementsByClassName('hl0'));" +
            //"document.getElementsByClassName('hl0').item(0).setSelectionRange(sel.anchorNode, sel.focusNode);}"
            "var textRange = document.body.createTextRange();" +
            "textRange.moveToElementText(document.getElementsByClassName('hl0'));"+
            "textRange.select();}"
*/


@JavascriptInterface
fun copy() : String = "javascript:" +
        "function copySelection(){" +
        "var sel = window.getSelection(); " +
        "var range = sel.getRangeAt(0); " +
        "var str = sel.toString();" +
        "window.android.setMessage(str);}"