USE [AIS]
GO

/****** Object:  Table [dbo].[t_product_daily_user]    Script Date: 09/28/2018 14:24:09 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
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


