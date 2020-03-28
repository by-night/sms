<template>
  <div class="s-canvas">
    <canvas id="s-canvas" :width="contentWidth" :height="contentHeight"></canvas>
  </div>
</template>
<script>
  export default {
    name: 'SIdentify',
    props: {
      identifyCode: {
        type: String,
        default: 'asdf'
      },

    },
    data(){
      return {
        fontSizeMin: 160,
        fontSizeMax: 40,
        backgroundColorMin: 180,
        backgroundColorMax:240,
        colorMin: 50,
        colorMax: 160,
        lineColorMin:40,
        lineColorMax: 180,
        dotColorMin: 0,
        dotColorMax: 255,
        contentWidth: 112,
        contentHeight: 38
      }
    },
    methods: {
      // 生成一个随机数
      randomNum(min, max) {
        return Math.floor(Math.random() * (max - min) + min)
      },
      // 生成一个随机的颜色
      randomColor(min, max) {
        let r = this.randomNum(min, max)
        let g = this.randomNum(min, max)
        let b = this.randomNum(min, max)
        return 'rgb(' + r + ',' + g + ',' + b + ')'
      },
      drawPic() {
        let canvas = document.getElementById('s-canvas')
        let ctx = canvas.getContext('2d')
        // ctx.textBaseline = 'bottom'
        // 绘制背景
        ctx.fillStyle = this.randomColor(this.backgroundColorMin, this.backgroundColorMax) //图形填充颜色设置
        ctx.strokeStyle = this.randomColor(this.backgroundColorMin, this.backgroundColorMax) //图形轮廓的颜色设置
        ctx.fillRect(0, 0, this.contentWidth, this.contentHeight) //绘制一个填充的矩形 0 0 width height x起点 y起点  宽 高
        ctx.strokeRect(0,0,this.contentWidth, this.contentHeight) // 绘制一个矩形边框 0 0 width height x起点 y起点  宽 高
        // ctx.clearRect(50,0,this.contentWidth,this.contentHeight)  //清除指定矩形区域，清除部分完全透明
        // 绘制文字
        for (let i = 0; i < this.identifyCode.length; i++) {
          this.drawText(ctx, this.identifyCode[i], i)
        }
        this.drawLine(ctx)
        this.drawDot(ctx)
      },
      drawText(ctx, txt, i) {
        ctx.fillStyle = this.randomColor(this.colorMin, this.colorMax)
        ctx.font = this.randomNum(this.fontSizeMin, this.fontSizeMax) + 'px SimHei' //字体大小
        ctx.textBaseline = 'alphabetic' //基线对齐
        let x = (i + 1) * (this.contentWidth / (this.identifyCode.length + 1))
        let y = this.randomNum(this.fontSizeMax, this.contentHeight - 5)
        var deg = this.randomNum(-45, 45)
        // 修改坐标原点和旋转角度
        ctx.translate(x, y)//移动不同位置  参数偏移量
        ctx.rotate(deg * Math.PI / 180) //旋转 参数角度
        ctx.fillText(txt, 0, 0)
        // 恢复坐标原点和旋转角度
        ctx.rotate(-deg * Math.PI / 180)
        ctx.translate(-x, -y)
      },
      drawLine(ctx) {
        // 绘制干扰线
        for (let i = 0; i < 8; i++) {
          ctx.strokeStyle = this.randomColor(this.lineColorMin, this.lineColorMax)
          ctx.beginPath() //新建一条路径
          ctx.moveTo(this.randomNum(0, this.contentWidth), this.randomNum(0, this.contentHeight)) //设置起点x,y
          ctx.lineTo(this.randomNum(0, this.contentWidth), this.randomNum(0, this.contentHeight)) //绘制直线 x,y 一条当前位置到x,y点的直线
          ctx.stroke() // 通过线条绘制图形轮廓
          // ctx.closePath() //结束闭合路径
        }
      },
      drawDot(ctx) {
        // 绘制干扰点
        for (let i = 0; i < 100; i++) {
          ctx.fillStyle = this.randomColor(0, 255)
          ctx.beginPath()
          // 绘制圆弧或圆，x,y,radius，startAngle,endAngle,anticlockwise // x,y 圆心点，radius 半径，从startAngle开始到endAngle结束
          ctx.arc(this.randomNum(0, this.contentWidth), this.randomNum(0, this.contentHeight), 1, 0, 2 * Math.PI)
          ctx.fill() //通过填充路径的内容区域生成实心的图形。
        }
      }
    },
    watch: {
      identifyCode() {
        this.drawPic()
      }
    },
    mounted() {
      this.drawPic()
    }
  }
</script>
