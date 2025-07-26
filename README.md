# CheckIn24

CheckIn24 is an offline, Material You, check in client for 24 Hour Fitness gym memberships 

## How it works

The data written in the check in qr code on the official 24go app is a JSON object in the following format: 

```json
{
  "SR":"24GO",
  "MB":"MBR00000000",
  "DT":1752738571206,
  "TP":"P",
  "OS":"Android",
  "AP":"1.78.1",
  "DI":"00000000-0000-0000-0000-000000000000"
}
```
- `SR` app name. default value: `"24GO"`
- `MB` user member number preceded by `"MBR"`
- `DT` unix time stamp in millis
- `TP` honestly don't know what this means but default value is `"P"`
- `OS` device operating system. in this case `"Android"`
- `AP` 24go app version. `"1.78.1"` at the time of 1.0.0 release. will update value as necessary to maintain functionality
- `DI` device advertising id. for a device with no advertising id this value is `"00000000-0000-0000-0000-000000000000"` which is also the value this app uses

## Disclaimer 

This application comes with no guarantee of functionality or adherence to 24 Hour Fitness policy. It works for me. It might work for you. Use at your own discretion 
