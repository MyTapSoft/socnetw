var audio = new Audio('menu.wav');
audio.type = 'audio/wav';

var playPromise = audio.play();

if (playPromise !== undefined) {
    playPromise.then(function () {
        console.log('Playing....');
    }).catch(function (error) {
        console.log('Failed to play....' + error);
    });
}
