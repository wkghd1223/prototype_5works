#include <jni.h>
#include <iostream>
#include <opencv2/opencv.hpp>
#include <android/log.h>

using namespace std;
using namespace cv;

// 렌즈합성시 색상 뒤집힘 현상 발생때문에 넣어줌
void InvertColor( Mat &input_img, Mat &inverted_img){
    CV_Assert( input_img.type() == CV_8UC3);
    inverted_img = input_img.clone();

    for (int row = 0 ; row < input_img.rows ; row++){
        for(int col = 0; col < input_img.cols ; col++){
            for (int ch = 0; ch <input_img.channels(); ch ++){
                inverted_img.at<Vec3b>(row, col)[ch] = 255 - inverted_img.at<Vec3b>(row, col)[ch];
            }
        }
    }
}
// 렌즈합성 함수
void overlayImage(const Mat &background, const Mat &foreground,
                  Mat &output, Point2i location)
{
    background.copyTo(output);

    // start at the row indicated by location, or at row 0 if location.y is negative.
    for (int y = max(location.y, 0); y < background.rows; ++y)
    {
        int fY = y - location.y; // because of the translation
        // we are done of we have processed all rows of the foreground image.
        if (fY >= foreground.rows){
            break;
        }
        // start at the column indicated by location,
        // or at column 0 if location.x is negative.
        for (int x = max(location.x, 0); x < background.cols; ++x)
        {
            int fX = x - location.x; // because of the translation.
            // we are done with this row if the column is outside of the foreground image.
            if (fX >= foreground.cols){
                break;
            }
            // determine the opacity of the foregrond pixel, using its fourth (alpha) channel.
            double opacity =
                    ((double)foreground.data[fY * foreground.step + fX * foreground.channels() + 3])
                    / 255.;
            // and now combine the background and foreground pixel, using the opacity,
            // but only if opacity > 0.
            for (int c = 0; opacity > 0 && c < output.channels(); ++c)
            {
                unsigned char foregroundPx =
                        foreground.data[fY * foreground.step + fX * foreground.channels() + c];
                unsigned char backgroundPx =
                        background.data[y * background.step + x * background.channels() + c];
                output.data[y*output.step + output.channels()*x + c] =
                        backgroundPx * (1. - opacity) + foregroundPx * opacity;
            }
        }
    }
}

// 기존의 resize 함수를 오버로딩하여 resize한 비율을 리턴해주는 함수 정의
float resize(Mat img_src, Mat &img_resize, int resize_width){
    float scale = resize_width / (float)img_src.cols ;
    if (img_src.cols > resize_width) {
        int new_height = cvRound(img_src.rows * scale);
        resize(img_src, img_resize, Size(resize_width, new_height));
    }
    else {
        img_resize = img_src;
    }
    return scale;
}

// 얼굴 및 눈 검출 코드
// 얼굴을 먼저 검출한 후 검출된 얼굴에서 눈을 검출한다.
extern "C"
JNIEXPORT void JNICALL
Java_com_example_prototype_15works_ui_camera_CameraFragment_detect(JNIEnv *env, jobject thiz,
                                                    jlong cascadeClassifier_face,
                                                    jlong cascadeClassifier_eye,
                                                    jlong mat_addr_input, jlong mat_addr_result,
                                                    jstring lenze_name) {
    Mat &img_input = *(Mat *) mat_addr_input;
    Mat &img_result = *(Mat *) mat_addr_result;
    img_result = img_input.clone();

    const int SIZE = 140;

    const char *nativeFileNameString = env->GetStringUTFChars(lenze_name, 0);
    string baseDir("/storage/emulated/0/");
    baseDir.append(nativeFileNameString);
    const char *pathDir = baseDir.c_str();
    __android_log_print(ANDROID_LOG_DEBUG, "native-lib :: ",
                        "Path of Lenze :  %s", pathDir);


    Mat lenze = imread(pathDir);
    if(lenze.empty()) { return;}

    vector<Rect> faces;
    Mat img_gray;

    cvtColor(img_input, img_gray, COLOR_BGR2GRAY);
    equalizeHist(img_gray, img_gray); // 회색 사을 더욱 선명하게 해줌
    Mat img_resize;
    float resizeRatio = resize(img_gray, img_resize, SIZE);

    //-- Detect faces
    ((CascadeClassifier *) cascadeClassifier_face)->detectMultiScale(img_resize, faces, 1.3, 2, 0|CASCADE_SCALE_IMAGE, Size(50, 50) );

//    __android_log_print(ANDROID_LOG_DEBUG, (char *) "native-lib :: ",
//                        (char *) "face %d found ", faces.size());

    for (int i = 0; i < faces.size(); i++) {
        double real_facesize_x = faces[i].x / resizeRatio;
        double real_facesize_y = faces[i].y / resizeRatio;
        double real_facesize_width = faces[i].width / resizeRatio;
        double real_facesize_height = faces[i].height / resizeRatio;

//        Point center( real_facesize_x + real_facesize_width / 2, real_facesize_y + real_facesize_height/2);
//        ellipse(img_result, center, Size( real_facesize_width / 2, real_facesize_height / 2), 0, 0, 360,
//                Scalar(255, 0, 255), 30, 8, 0);

        Rect face_area(real_facesize_x, real_facesize_y, real_facesize_width,real_facesize_height);
        Mat faceROI = img_gray( face_area );

        vector<Rect> eyes;
        //-- In each face, detect eyes
        ((CascadeClassifier *) cascadeClassifier_eye)->detectMultiScale( faceROI, eyes, 1.3, 3, 0 |CASCADE_SCALE_IMAGE, Size(70,70) );

        for ( size_t j = 0; j < eyes.size(); j++ )
        {
            Point eye_center( real_facesize_x  + eyes[j].x + eyes[j].width/4,
                              real_facesize_y + eyes[j].y  + eyes[j].height/4);

//            눈에 원 그리기
//            int radius = cvRound( (eyes[j].width + eyes[j].height)*0.25 * 0.5 );
//            circle( img_result, eye_center, radius, Scalar( 255, 255, 255 ), 10, 8, 0 );

//            렌즈와 눈 이미지 합성 (렌즈 이미지 사이즈 얻기)
//            return;
            __android_log_print(ANDROID_LOG_DEBUG, (char *) "native-lib :: ",
                                (char *) "asdf : %d, %d, %d, %d ",
                                eye_center.x - lenze.size().width/2 ,
                                eye_center.y - lenze.size().height/2 ,
                                lenze.cols ,
                                lenze.rows);

            Mat resized_lenze;
            Mat inverted_lenze;
            InvertColor(lenze, inverted_lenze);
            resize(inverted_lenze, resized_lenze, Size(eyes[j].width/2 ,eyes[j].height/2), 0 , 0 );

            Mat output;
            img_result.copyTo(output);
            overlayImage(output, resized_lenze,img_result, eye_center);

        }
    }
}
// .xml 파일을 가져와 CascadeClassifier객체로 로딩해주는 함수
extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_prototype_15works_ui_camera_CameraFragment_loadCascade(JNIEnv *env, jobject thiz,
                                                         jstring cascade_file_name) {
    const char *nativeFileNameString = env->GetStringUTFChars(cascade_file_name, 0);
    string baseDir("/storage/emulated/0/");
    baseDir.append(nativeFileNameString);
    const char *pathDir = baseDir.c_str();
    jlong ret = 0;
    ret = (jlong) new CascadeClassifier(pathDir);
    if (((CascadeClassifier *) ret)->empty()) {
        __android_log_print(ANDROID_LOG_DEBUG, "native-lib :: ",
                            "CascadeClassifier로 로딩 실패  %s", nativeFileNameString);
    }
    else
        __android_log_print(ANDROID_LOG_DEBUG, "native-lib :: ",
                            "CascadeClassifier로 로딩 성공 %s", nativeFileNameString);
    env->ReleaseStringUTFChars(cascade_file_name, nativeFileNameString);
    return ret;
}