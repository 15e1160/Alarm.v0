# アラーム アプリ作ってみた
現役大学生のボイスが聞けるアラームアプリになるはずだった。

今は音が鳴らない目覚まし(まだ鳴らす段階に至ってません)

とりあえずここから適当にダウンロードして何とかしてください。

ファイルについて下に書いたのであとは本を見ながらいい感じに作ってください。

(レポジトリばかり増える。GitHubの使い方がよくわからないです)

# ファイルの見方

ファイルでなかったりおかしかったら再ダウンロードしてみてください。一応確かめてみたけど不安なので

appの中からjavaを選択→com.example.mikan.myalarmclockを選択

１、AlarmBroadcastReceiver-アラーム処理のプログラム(多分もう触ることはないでしょう)

２、Daialog.kt-アラートダイアログ。通知とかトーストとかそういうの

３、MainActivity-このアプリの肝ですね。基本的にこれを触る感じです。多分

・resファイル

１、layout→activity_main.xml-このアプリのインターフェイス(時間があればかっこいい感じに仕上げてください270ページ)

２、raw-ここにogg形式の音楽をぶち込んでください。215ページからやり方書いてるのでそれ見てください。

IDは(例)a1.ogg　数字は半角で入力。曲増やすごとに数字も増やす感じで。a2.ogg a3.oggみたいに

めんどくさくなったら変えます。
 
 # 今後やること
 ・Gitについて理解しておく。　https://git-scm.com/book/ja/v2　
 
 ・232ページからのsoundpoolを使ってアラームが鳴るようにすること。多分だけどMainActivityにうまい感じに入れれたらいいのかも。
 
 ・アラーム選択して選択されたアラームが鳴るような仕組みを作る(多分Spinner使う感じ)
 
 ・レイアウトいじりなおしたい
 
 # 12/4追記
 ・soundpoolのプログラム追加←設定時間になると処理落ちする現象(未解決)時間ある時に櫻井先生に対処法を聞いておいてください。
 
 ・卒業制作の仕様書作っておいてください。
 
 
 
