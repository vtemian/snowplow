/*
 * Copyright (c) 2012-2013 SnowPlow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package com.snowplowanalytics.snowplow.hadoop.hive

// Specs2
import org.specs2.mutable.Specification

// SnowPlow Utils
import com.snowplowanalytics.util.Tap._

// Deserializer
import test.{SnowPlowDeserializer, SnowPlowEvent}

class DeviceTimestampTest extends Specification {

  // Toggle if tests are failing and you want to inspect the struct contents
  implicit val _DEBUG = false

  val row = "2012-05-21\t07:14:47\tFRA2\t3343\t83.4.209.35\tGET\td3t05xllj8hhgj.cloudfront.net\t/ice.png\t200\thttps://test.psybazaar.com/shop/checkout/\tMozilla/5.0%20(X11;%20Ubuntu;%20Linux%20x86_64;%20rv:11.0)%20Gecko/20100101%20Firefox/11.0\t&page=Test&ev_ca=ecomm&ev_ac=checkout&ev_la=id_email&ev_va=Empty&ev_pr=ERROR&tid=236095&dtm=1361369789316&refr=http%253A%252F%252Ftest.psybazaar.com%252F&uid=135f6b7536aff045&lang=en-US&vid=5&f_pdf=0&f_qt=1&f_realp=0&f_wma=1&f_dir=0&f_fla=1&f_java=1&f_gears=0&f_ag=0&res=1920x1080&cookie=1"
  
  val expected = new SnowPlowEvent().tap { e =>
    e.dvce_dt = "2013-02-20"
    e.dvce_tm = "14:16:29"
    e.dvce_epoch = 1361369789316L
  }
  
  "A SnowPlow row with a client-set timestamp" should {

    val actual = SnowPlowDeserializer.deserialize(row)

    "have dvce_dt (Device Date) = %s".format(expected.dvce_dt) in {
      actual.dvce_dt must_== expected.dvce_dt
    }
    "have dvce_tm (Device Time) = %s".format(expected.dvce_tm) in {
      actual.dvce_tm must_== expected.dvce_tm
    }
    "have dvce_epoch (Device Milliseconds since Epoch) = %s".format(expected.dvce_epoch) in {
      actual.dvce_epoch must_== expected.dvce_epoch
    }
  }
}
