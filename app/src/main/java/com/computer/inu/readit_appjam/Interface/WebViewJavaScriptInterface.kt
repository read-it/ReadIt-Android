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
            "console.log(j);" +
            "for(i in parseString){" +
            "var newRange;" +
            "var start = document.caretRangeFromPoint(parseString[i].left,parseString[i].top);" +
            "var end = document.caretRangeFromPoint(parseString[i].right,parseString[i].top);" +
            "var newNode = document.createElement('span');" +
            "var color = parseString[i].color;" +
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
            "var result = {};" +
            "for(var i = 0 ; i < highlightRects.length ; i++){" +
            "result[i] = {};" +
            "result[i].left = highlightRects[i].left + window.pageXOffset;" +
            "result[i].top = highlightRects[i].top + window.pageYOffset;" +
            "result[i].right = highlightRects[i].right + window.pageXOffset;" +
            "result[i].bottom = highlightRects[i].bottom + window.pageYOffset;" +
            "result[i].color = color;" +
            "}" +
            "console.log(JSON.stringify(result));" +
            "var data = {};" +
            "data.highlight_text = range.toString();" +
            "data.result = result;" +
            "data.color = color;" +
            "return JSON.stringify(data);}" +
            "function show_alert(){" +
            "window.alert(range.startOffset.toString()+range.endOffset.toString());}"

@JavascriptInterface
fun copy(): String = "javascript:" +
        "function copySelection(){" +
        "var sel = window.getSelection(); " +
        "var range = sel.getRangeAt(0); " +
        "var str = sel.toString();" +
        "window.android.setMessage(str);}"