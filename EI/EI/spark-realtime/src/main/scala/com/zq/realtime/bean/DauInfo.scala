package com.zq.realtime.bean

case class DauInfo(
                    var num:String,
                    var work_name:String,
                    var location:String,
                    var salary:String,
                    var work_year:String,
                    var educate:String,
                    var company_name:String,
                    var company_type:String,
                    var financing_conditions:String,
                    var company_size:String,
                    var city:String,
                    var nums:String,
                  ) {

  def this(){
    this(null,null,null,null,null,null,null,null,null,null ,null ,null)
  }

}
