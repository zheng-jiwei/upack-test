# upackテストのシナリオ
- サイトにアクセスできることの確認
  - http://localhost:8080/index.html をアクセスする
    - status=200が返される
- サイトからっデータ取得の確認
  - http://localhost:8080/provinces をアクセスしする
    - status=200の確認
    - データ件数は 47 の確認
    - 47都道府県の地名確認（既存データですが、入力ミスの確認）
  - http://localhost:8080/box/sizes をアクセスする
    - status=200の確認
    - データ件数7の確認
    - サイズデータ（"60"/"80"/"100"/"120"/"140"/"160"/"170"）がすべてあるの確認
- 荷物サイズで計算した送料は正しいかの確認
  - 北海道からの送料テスト
    - http://localhost:8080/calculate へPOST {:from "北海道" :to "北海道" :size "60"}
      - status = 200 の確認
      - {:data 810}が返されるの確認
    - http://localhost:8080/calculate へPOST {:from "北海道" :to "青森" :size "60"}
      - status = 200 の確認
      - {:data 1100}が返されるの確認
    - ... (北海道から各地方へのテスト)
  - 岩手からの送料テスト
    - http://localhost:8080/calculate へPOST {:from "岩手" :to "岩手" :size "80"}
      - status = 200 の確認
      - {:data 810}が返されるの確認
    - http://localhost:8080/calculate へPOST {:from "岩手" :to "北海道" :size "80"}
      - status = 200 の確認
      - {:data 1310}が返されるの確認
    - ... (岩手から各地方へのテスト)
   - ...(各地方からの送料テスト)
- 間違いサイズならばエラーが出るの確認
  - http://localhost:8080/calculate へPOST {:from "岩手" :to "岩手" :size 180}
    - status = 200 の確認
    - {:error "荷物のサイズは170cmまで受付できます。"}が返されるの確認
  - http://localhost:8080/calculate へPOST {:from "岩手" :to "岩手" :size "180cm"}
    - status = 200 の確認
    - {:error "サイズの入力は数字のみ、または数字のみあるの文字列ができます。"}が返されるの確認
- サイズは数字ではなくでも問題ないの確認
  - http://localhost:8080/calculate へPOST {:from "岩手" :to "岩手" :size "80"}
    - status = 200 の確認
    - {:data 1310}が返されるの確認
- 複数荷物を同梱して一番安い料金適用のテスト
  - 荷物は 30x20x20と20x10x18を東京から熊本へ; Then 運賃は(80cm)1530円
    - http://localhost:8080/best/price へPOST {:from "東京" :to "熊本" :size [[30 20 20] [20 10 18]]}
      - status = 200 の確認
      - {:data 1530}が返されるの確認
  - 荷物は 30x20x20と10x10x18と10x3x20を東京から熊本へ; Then 運賃は(100cm)1760円    
    - http://localhost:8080/best/price へPOST {:from "東京" :to "熊本" :size [[30 20 20] [20 10 18] [10 3 20]]}
      - status = 200 の確認
      - {:data 1760}が返されるの確認
  - 荷物は 30x20x20と10x10x18と10x3x20と60x5x5を東京から熊本へ; Then 運賃は(120cm) 2260 円
    - http://localhost:8080/best/price へPOST {:from "東京" :to "熊本" :size [[30 20 20] [10 10 18] [10 3 20] [60 5 5]]}
      - status = 200 の確認
      - {:data 2260}が返されるの確認
  - 荷物は 30x20x20と20x10x20と10x8x8、8x8x12を東京から熊本へ; Then 運賃は(80cm)1530円
    - http://localhost:8080/best/price へPOST {:from "東京" :to "熊本" :size [[30 20 20] [20 10 20] [10 8 8] [8 8 12]]}
      - status = 200 の確認
      - {:data 1530}が返されるの確認
- 同梱したら 170cm 超えるなら、二つに分けて同梱のテスト
  - 荷物は 90x50x15と80x50x20を東京から熊本へ; Then 運賃は(160+160cm) 4980円
    - http://localhost:8080/best/price へPOST {:from "東京" :to "熊本" :size [[90 50 15] [80 50 20]]}
      - status = 200 の確認
      - {:data 4980}が返されるの確認
    - 荷物は 40x40x40と100x5x5を東京から熊本へ; Then 運賃は(160cm+120) 4510 円
      - http://localhost:8080/best/price へPOST {:from "東京" :to "熊本" :size [[40 40 40] [100 5 5]]}
        - status = 200 の確認
        - {:data 4510}が返されるの確認
    - 荷物は 80x40x20と80x40x20と80x40x20を東京から熊本へ; Then 運賃は(160+140cm) 4750 円
      - http://localhost:8080/best/price へPOST {:from "東京" :to "熊本" :size [[80 40 20] [80 40 20] [80 40 20]]}
        - status = 200 の確認
        - {:data 4750}が返されるの確認


# upack-test
- コマンドラインテスト
```
    lein test
```

- ブラウザでのテスト
```
    lein ring server-headless
```
でプログラムのプロセスを起動する  
その後、ブラウザで下記のurlをアクセスしたら確認できる画面がでる
```
    http://localhost:8080/index.html
```
