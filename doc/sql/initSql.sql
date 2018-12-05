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
	[productDate] [datetime] NULL,
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
	[createUser] [nvarchar](50) NULL,
	[createTime] [datetime] NULL,
	[modifyUser] [nvarchar](50) NULL,
	[modifyTime] [datetime] NULL,
	[isPiecework] [smallint] NULL,
	[isDelete] [smallint] NULL,
	[sequence] [smallint] NULL,
 CONSTRAINT [PK_t_product_daily_bill_detail] PRIMARY KEY CLUSTERED 
(
	[dailyNo] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO



CREATE TABLE [dbo].[t_product_daily_bill_total](
	[billNo] [nvarchar](50) NOT NULL,
	[materialCode] [nvarchar](50) NULL,
	[materialName] [nvarchar](50) NULL,
	[model] [nvarchar](50) NULL,
	[planQuantity] [int] NULL,
	[resProcess1] [nvarchar](50) NULL,
	[resProcessPrice1] [decimal](18, 4) NULL,
	[resProcessTotalQty1] [int] NULL,
	[resProcess2] [nvarchar](50) NULL,
	[resProcessPrice2] [decimal](18, 4) NULL,
	[resProcessTotalQty2] [int] NULL,
	[resProcess3] [nvarchar](50) NULL,
	[resProcessPrice3] [decimal](18, 4) NULL,
	[resProcessTotalQty3] [int] NULL,
	[process1] [nvarchar](50) NULL,
	[processPrice1] [decimal](18, 4) NULL,
	[processTotalQty1] [int] NULL,
	[process2] [nvarchar](50) NULL,
	[processPrice2] [decimal](18, 4) NULL,
	[processTotalQty2] [int] NULL,
	[process3] [nvarchar](50) NULL,
	[processPrice3] [decimal](18, 4) NULL,
	[processTotalQty3] [int] NULL,
	[process4] [nvarchar](50) NULL,
	[processPrice4] [decimal](18, 4) NULL,
	[processTotalQty4] [int] NULL,
	[process5] [nvarchar](50) NULL,
	[processPrice5] [decimal](18, 4) NULL,
	[processTotalQty5] [int] NULL,
	[process6] [nvarchar](50) NULL,
	[processPrice6] [decimal](18, 4) NULL,
	[processTotalQty6] [int] NULL,
 CONSTRAINT [PK_t_product_daily_bill_total] PRIMARY KEY CLUSTERED 
(
	[billNo] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


CREATE TABLE [dbo].[t_product_daily_user](
	[userNo] [nvarchar](50) NOT NULL,
	[userName] [nvarchar](50) NULL,
	[password] [nvarchar](max) NULL,
	[isPiecework] [smallint] NULL,
	[isManager] [smallint] NULL,
	[isDisable] [smallint] NULL,
	[createUser] [nvarchar](50) NULL,
	[createTime] [datetime] NULL,
	[LastModifyTime] [datetime] NULL,
	[isDelete] [smallint] NULL,
PRIMARY KEY CLUSTERED 
(
	[userNo] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[t_product_daily_user] ADD  CONSTRAINT [DF_t_product_daily_user_isDisable]  DEFAULT ((0)) FOR [isDisable]
GO

ALTER TABLE [dbo].[t_product_daily_user] ADD  CONSTRAINT [DF_t_product_daily_user_isDelete]  DEFAULT ((0)) FOR [isDelete]
GO


INSERT INTO [AIS].[dbo].[t_product_daily_user]
           ([userNo]
           ,[userName]
           ,[password]
           ,[isPiecework]
           ,[isManager]
           ,[isDisable]
           ,[createUser]
           ,[createTime]
           ,[LastModifyTime]
           ,[isDelete])
     VALUES ('0001', '管理员', 'lueSGJZetyySpUndWjMBEg==', 1, 1, 0, NULL, '2018-11-11 00:00:00.000',NULL, 0);
           
GO


ALTER TABLE dbo.t_product_daily_user ADD isAuditor smallint NULL


CREATE TABLE [dbo].[t_product_daily_work](
	[workNo] [nvarchar](50) NOT NULL,
	[workName] [nvarchar](50) NULL,
	[unit] [nvarchar](50) NULL,
	[unitPrice] [numeric](18, 4) NULL,
	[status] [smallint] NULL,
 CONSTRAINT [PK_t_product_daily_work] PRIMARY KEY CLUSTERED 
(
	[workNo] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]



