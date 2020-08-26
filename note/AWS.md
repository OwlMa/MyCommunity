## Upload the article to the AWS s3
- For the safety reason and convenience, I decide to upload all the articles and images in the article to the AWS s3.
- In the database article body, we only store the ObjectKey of the article, so next time when we want to view the article,
we can get it from the aws s3 bucket and put it in the body of the article.

## Pre-sign URL problem
*Problem*
- The pre-sign url produced by the AWS S3 can make us view the file we upload to the s3 bucket before the expiration time.
- But after tha time, this url is out of use, which means we can not try to view this file by this url.
- Therefore, we need a strategy to produce a new url every time when we try to view this object(especially for an img url) from the original url.
- The Pre-sign URL is like: (https://bucketname.s3.amazonaws.com/ObjectKey/IMG_8039.JPG?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=...)

*Solution*
- When we get the full content from the articles in the s3 bucket by pre-sign url, we find all the img url by using the 
regex and replace it with a new pre-sign url(see AWSProvider.replaceImgURL).



