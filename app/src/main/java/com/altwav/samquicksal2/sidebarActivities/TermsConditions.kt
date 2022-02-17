package com.altwav.samquicksal2.sidebarActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altwav.samquicksal2.R
import kotlinx.android.synthetic.main.activity_register2.*
import kotlinx.android.synthetic.main.activity_terms_conditions.*

class TermsConditions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_conditions)

        btn_terms_back.setOnClickListener {
            finish()
        }

        tvEULASidebar.text = "This End-User License Agreement (\"EULA\") is a legal agreement between you and ALTWAV. Our EULA was created by EULA Template for Samquicksal." +
                "\n\nThis EULA agreement governs your acquisition and use of our Samquicksal software (\"Software\") directly from ALTWAV or indirectly through a ALTWAV authorized reseller or distributor (a \"Reseller\")." +
                "\n\nPlease read this EULA agreement carefully before completing the installation process and using the Samquicksal software. It provides a license to use the Samquicksal software and contains warranty information and liability disclaimers." +
                "\n\nIf you register for a free trial of the Samquicksal software, this EULA agreement will also govern that trial. By clicking \"accept\" or installing and/or using the Samquicksal software, you are confirming your acceptance of the Software and agreeing to become bound by the terms of this EULA agreement." +
                "\n\nIf you are entering into this EULA agreement on behalf of a company or other legal entity, you represent that you have the authority to bind such entity and its affiliates to these terms and conditions. If you do not have such authority or if you do not agree with the terms and conditions of this EULA agreement, do not install or use the Software, and you must not accept this EULA agreement." +
                "\n\nThis EULA agreement shall apply only to the Software supplied by ALTWAV herewith regardless of whether other software is referred to or described herein. The terms also apply to any ALTWAV updates, supplements, Internet-based services, and support services for the Software, unless other terms accompany those items on delivery. If so, those terms apply." +
                "\n\nLicense Grant" +
                "\nALTWAV hereby grants you a personal, non-transferable, non-exclusive licence to use the Samquicksal software on your devices in accordance with the terms of this EULA agreement." +
                "\n\nYou are permitted to load the Samquicksal software (for example a PC, laptop, mobile or tablet) under your control. You are responsible for ensuring your device meets the minimum requirements of the Samquicksal software." +
                "\n\nYou are not permitted to:" +
                "\n1. Edit, alter, modify, adapt, translate or otherwise change the whole or any part of the Software nor permit the whole or any part of the Software to be combined with or become incorporated in any other software, nor decompile, disassemble or reverse engineer the Software or attempt to do any such things." +
                "\n2. Reproduce, copy, distribute, resell or otherwise use the Software for any commercial purpose." +
                "\n3. Allow any third party to use the Software on behalf of or for the benefit of any third party." +
                "\n4. Use the Software in any way which breaches any applicable local, national or international law." +
                "\n5. Use the Software for any purpose that ALTWAV considers is a breach of this EULA agreement." +
                "\n6. Intellectual Property and Ownership." +
                "\n7. ALTWAV shall at all times retain ownership of the Software as originally downloaded by you and all subsequent downloads of the Software by you. The Software (and the copyright, and other intellectual property rights of whatever nature in the Software, including any modifications made thereto) are and shall remain the property of ALTWAV." +
                "\n\nALTWAV reserves the right to grant licences to use the Software to third parties." +
                "\n\nTermination" +
                "\nThis EULA agreement is effective from the date you first use the Software and shall continue until terminated. You may terminate it at any time upon written notice to ALTWAV." +
                "\n\nIt will also terminate immediately if you fail to comply with any term of this EULA agreement. Upon such termination, the licenses granted by this EULA agreement will immediately terminate and you agree to stop all access and use of the Software. The provisions that by their nature continue and survive will survive any termination of this EULA agreement." +
                "\n\nGoverning Law" +
                "\nThis EULA agreement, and any dispute arising out of or in connection with this EULA agreement, shall be governed by and construed in accordance with the laws of ph."


    }
}