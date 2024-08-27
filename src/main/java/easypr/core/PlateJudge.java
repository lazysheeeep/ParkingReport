package easypr.core;

import static org.bytedeco.opencv.global.opencv_core.CV_32FC1;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_ml.SVM;

import java.awt.*;
import java.util.Vector;

public class PlateJudge {

  private SVM svm;

  // 用于从车牌的image生成svm的训练特征features
  private SVMCallback features;

  // 模型储存路径
  private String path = "resources/model/svm.xml";


  public PlateJudge() {
    this.svm = SVM.create();
    loadModel();
  }

  public void loadModel() {
    loadModel(path);
  }

  public void loadModel(String s) {
    svm.clear();
    svm = SVM.load(s);
  }

  public void setModelPath(String path) {this.path = path;}

  public final String getModelPath() {
    return path;
  }

  // 对单幅图像进行svm判断
  public int plateJudge(final Mat inMat) {
    // 获取图像的直方图特征
    Mat features = this.features.getHistogramFeatures(inMat);

    // 通过直方图均衡化后的彩色图进行预测
    // 重塑特征矩阵
    Mat p = features.reshape(1,1);
    // 转换数据类型为32位浮点数，以满足SVM模型的输入格式
    p.convertTo(p, CV_32FC1);
    // 预测 使用SVM模型对特征进行预测
    float ret = svm.predict(features);

    return (int) ret;
  }

  public int plateJudge(Vector<Mat> inVec, Vector<Mat> resultVec) {

    // 遍历inVec中的图像
    for (int j = 0; j < inVec.size(); j++) {
      Mat inMat = inVec.get(j);

      // 满足条件的inMat，加入到resultVec
      if (1 == plateJudge(inMat)) {
        resultVec.add(inMat);
      } else { // 不满足进步一处理，再取中间部分判断一次
        // 获取图像宽高
        int w = inMat.cols();
        int h = inMat.rows();

        // 克隆图像方便进一步处理 看不懂去chat
        Mat tmpDes = inMat.clone();
        Mat tmpMat = new Mat(inMat, new Rect((int) (w * 0.05), (int) (h * 0.1), (int) (w * 0.9),
                (int) (h * 0.8)));
        resize(tmpMat, tmpDes, new Size(inMat.size()));

        if (plateJudge(tmpDes) == 1) {
          resultVec.add(inMat);
        }
      }
    }

    return 0;
  }
}
