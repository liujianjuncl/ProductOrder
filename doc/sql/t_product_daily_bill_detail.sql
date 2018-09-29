USE [AIS]
GO

/****** Object:  Table [dbo].[t_product_daily_bill_detail]    Script Date: 09/29/2018 21:22:54 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[t_product_daily_bill_detail](
	[dailyNo] [nvarchar](50) NOT NULL,
	[billNo] [nvarchar](50) NOT NULL,
	[materialCode] [nvarchar](50) NULL,
	[materialName] [nvarchar](50) NULL,
	[model] [nvarchar](50) NULL,
	[planQuantity] [int] NULL,
	[resProcess1] [nvarchar](50) NULL,
	[resProcessPrice1] [decimal](18, 10) NULL,
	[resProcessQty1] [int] NULL,
	[resProcess2] [nvarchar](50) NULL,
	[resProcessPrice2] [decimal](18, 4) NULL,
	[resProcessQty2] [int] NULL,
	[resProcess3] [nvarchar](50) NULL,
	[resProcessPrice3] [decimal](18, 4) NULL,
	[resProcessQty3] [int] NULL,
	[process1] [nvarchar](50) NULL,
	[processPrice1] [decimal](18, 4) NULL,
	[processQty1] [int] NULL,
	[process2] [nvarchar](50) NULL,
	[processPrice2] [decimal](18, 4) NULL,
	[processQty2] [int] NULL,
	[process3] [nvarchar](50) NULL,
	[processPrice3] [decimal](18, 4) NULL,
	[processQty3] [int] NULL,
	[process4] [nvarchar](50) NULL,
	[processPrice4] [decimal](18, 4) NULL,
	[processQty4] [int] NULL,
	[process5] [nvarchar](50) NULL,
	[processPrice5] [decimal](18, 4) NULL,
	[processQty5] [int] NULL,
	[process6] [nvarchar](50) NULL,
	[processPrice6] [decimal](18, 4) NULL,
	[processQty6] [int] NULL,
	[process7] [nvarchar](50) NULL,
	[processPrice7] [decimal](18, 4) NULL,
	[processQty7] [int] NULL,
	[process8] [nvarchar](50) NULL,
	[processPrice8] [decimal](18, 4) NULL,
	[processQty8] [int] NULL,
	[process9] [nvarchar](50) NULL,
	[processPrice9] [decimal](18, 4) NULL,
	[processQty9] [int] NULL,
	[createUser] [nvarchar](50) NULL,
	[createTime] [datetime] NULL,
	[isPiecework] [smallint] NULL,
	[isDelete] [smallint] NULL,
	[sequence] [smallint] NULL,
 CONSTRAINT [PK_t_product_daily_bill_detail] PRIMARY KEY CLUSTERED 
(
	[dailyNo] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


