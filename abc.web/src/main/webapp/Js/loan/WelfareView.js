try {
    UE.getEditor('Ueditor').destroy();
    UE.getEditor('Ueditor1').destroy();
    UE.getEditor('Ueditor2').destroy();
    UE.getEditor('Ueditor3').destroy();
    UE.getEditor('Ueditor4').destroy();
    UE.getEditor('Ueditor5').destroy();
    UE.getEditor('Ueditor6').destroy();
} catch (e) {
    UE.getEditor('Ueditor');
    UE.getEditor('Ueditor1');
    UE.getEditor('Ueditor2');
    UE.getEditor('Ueditor3');
    UE.getEditor('Ueditor4');
    UE.getEditor('Ueditor5');
    UE.getEditor('Ueditor6');
}