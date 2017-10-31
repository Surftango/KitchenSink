.class public Lcom/ca/android/app/MAAInterceptor;
.super Ljava/lang/Object;
.source "MAAInterceptor.java"

# interfaces
.implements Lokhttp3/Interceptor;


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 30
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public intercept(Lokhttp3/Interceptor$Chain;)Lokhttp3/Response;
    .locals 23
    .param p1, "chain"    # Lokhttp3/Interceptor$Chain;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 33
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v12

    .line 34
    .local v12, "startTime":J
    invoke-interface/range {p1 .. p1}, Lokhttp3/Interceptor$Chain;->request()Lokhttp3/Request;

    move-result-object v8

    .line 36
    .local v8, "request":Lokhttp3/Request;
    if-eqz v8, :cond_2

    .line 37
    invoke-virtual {v8}, Lokhttp3/Request;->url()Lokhttp3/HttpUrl;

    move-result-object v20

    invoke-virtual/range {v20 .. v20}, Lokhttp3/HttpUrl;->toString()Ljava/lang/String;

    move-result-object v15

    .line 38
    .local v15, "url":Ljava/lang/String;
    invoke-virtual {v15}, Ljava/lang/String;->length()I

    move-result v20

    move/from16 v0, v20

    int-to-long v0, v0

    move-wide/from16 v18, v0

    .line 39
    .local v18, "urlBytes":J
    const-wide/16 v16, 0x0

    .line 40
    .local v16, "urlBodyBytes":J
    invoke-virtual {v8}, Lokhttp3/Request;->body()Lokhttp3/RequestBody;

    move-result-object v20

    if-eqz v20, :cond_0

    .line 41
    invoke-virtual {v8}, Lokhttp3/Request;->body()Lokhttp3/RequestBody;

    move-result-object v20

    invoke-virtual/range {v20 .. v20}, Lokhttp3/RequestBody;->contentLength()J

    move-result-wide v16

    .line 43
    :cond_0
    add-long v4, v18, v16

    .line 44
    .local v4, "inBytes":J
    move-object/from16 v0, p1

    invoke-interface {v0, v8}, Lokhttp3/Interceptor$Chain;->proceed(Lokhttp3/Request;)Lokhttp3/Response;

    move-result-object v9

    .line 45
    .local v9, "response":Lokhttp3/Response;
    const-wide/16 v6, 0x0

    .line 46
    .local v6, "outBytes":J
    const/4 v14, 0x0

    .line 47
    .local v14, "statusCode":I
    if-eqz v9, :cond_1

    invoke-virtual {v9}, Lokhttp3/Response;->body()Lokhttp3/ResponseBody;

    move-result-object v20

    if-eqz v20, :cond_1

    .line 48
    invoke-virtual {v9}, Lokhttp3/Response;->body()Lokhttp3/ResponseBody;

    move-result-object v20

    invoke-virtual/range {v20 .. v20}, Lokhttp3/ResponseBody;->contentLength()J

    move-result-wide v6

    .line 49
    invoke-virtual {v9}, Lokhttp3/Response;->code()I

    move-result v14

    .line 51
    :cond_1
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v20

    sub-long v10, v20, v12

    .line 52
    .local v10, "responseTime":J
    long-to-int v0, v10

    move/from16 v20, v0

    long-to-int v0, v4

    move/from16 v21, v0

    long-to-int v0, v6

    move/from16 v22, v0

    move/from16 v0, v20

    move/from16 v1, v21

    move/from16 v2, v22

    invoke-static {v15, v14, v0, v1, v2}, Lcom/ca/android/app/CaMDOIntegration;->logNetworkEvent(Ljava/lang/String;IIII)V

    .line 55
    .end local v4    # "inBytes":J
    .end local v6    # "outBytes":J
    .end local v9    # "response":Lokhttp3/Response;
    .end local v10    # "responseTime":J
    .end local v14    # "statusCode":I
    .end local v15    # "url":Ljava/lang/String;
    .end local v16    # "urlBodyBytes":J
    .end local v18    # "urlBytes":J
    :goto_0
    return-object v9

    :cond_2
    move-object/from16 v0, p1

    invoke-interface {v0, v8}, Lokhttp3/Interceptor$Chain;->proceed(Lokhttp3/Request;)Lokhttp3/Response;

    move-result-object v9

    goto :goto_0
.end method
