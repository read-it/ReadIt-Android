package com.computer.inu.readit_appjam.Interface

import android.webkit.JavascriptInterface

interface WebViewJavaScriptInterface {
    fun WebViewJavaScriptInterface() {
    }
}

@JavascriptInterface
fun recoverHighlight(): String =
    "javascript:" +
            "function recoverHighlight(highlightList){" +
            "console.log(JSON.stringify(highlightList));" +
            "var selection = window.getSelection();" +
            "console.log(highlightList.length);" +
            "for(var j = 0 ; j < highlightList.length ; j++){" +
            "var parseString = highlightList[j];" +
            "for(i in parseString){" +
            "var newRange;" +
            "var start = document.caretRangeFromPoint(parseString[i].left,parseString[i].top);" +
            "var end = document.caretRangeFromPoint(parseString[i].right,parseString[i].top);" +
            "var newNode = document.createElement('span');" +
            "var color = 1;" +
            "if(color == 1)" +
            "newNode.setAttribute('style','background-color:#4EFF3D'); " +
            "else if(color == 2)" +
            "newNode.setAttribute('style','background-color:#06C8F3'); " +
            "else if(color == 3)" +
            "newNode.setAttribute('style','background-color:#ffffff'); " +
            "newRange = document.createRange();" +
            "newRange.setStart(start.startContainer,start.startOffset);" +
            "newRange.setEnd(end.startContainer,end.startOffset);" +
            "newNode.appendChild(newRange.extractContents());" +
            "newRange.insertNode(newNode);" +
            "selection.removeAllRanges();" +
            "selection.addRange(newRange);" +
            "}" +
            "}" +
            "console.log('hih')" +
            "}"

@JavascriptInterface
fun highlight(): String =
    "javascript:" +
            "function highlightSelection(color){" +
            "var sel = window.getSelection();" +
            "var range = sel.getRangeAt(0);" +
            "var selectedText = range.extractContents();" +
            "if(selectedText == null)" +
            "return;" +
            "var newNode = document.createElement('span'); " +
            "if(color == 1)" +
            "newNode.setAttribute('style','background-color:#4EFF3D'); " +
            "else if(color == 2)" +
            "newNode.setAttribute('style','background-color:#06C8F3'); " +
            "else if(color == 3)" +
            "newNode.setAttribute('style','background-color:#ffffff'); " +
            "newNode.appendChild(selectedText);" +
            "range.insertNode(newNode);" +

            "var highlightRects = range.getClientRects();" +
            "var result = JSON.parse(JSON.stringify(highlightRects));" +
            "console.log(result);" +
            "for(i = 0 ; i<highlightRects.length ; i++){" +
            "result[i].left += window.scrollX;" +
            "result[i].top += window.scrollY;" +
            "result[i].right + window.scrollX;" +
            "result[i].bottom += window.scrollY;" +
            "result[i].color = color;" +
            "}" +
            "for(i = 0 ; i<result.length ; i++){" +
            "result[i].left += window.scrollX;" +
            "result[i].top += window.scrollY;" +
            "result[i].right + window.scrollX;" +
            "result[i].bottom += window.scrollY;" +
            "result[i].color = color;" +
            "}" +
            "console.log(JSON.stringify(result));" +
            "return JSON.stringify(result);}" +
            "function show_alert(){" +
            "window.alert(range.startOffset.toString() + range.endOffset.toString());}"

@JavascriptInterface
fun copy(): String = "javascript:" +
        "function copySelection(){" +
        "var sel = window.getSelection(); " +
        "var range = sel.getRangeAt(0); " +
        "var str = sel.toString();" +
        "window.android.setMessage(str);}"