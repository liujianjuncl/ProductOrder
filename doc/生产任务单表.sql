USE [AIS]
GO

/****** Object:  Table [dbo].[ICMO]    Script Date: 09/16/2018 18:14:00 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ICMO](
	[FBrNo] [varchar](10) NOT NULL,
	[FInterID] [int] NOT NULL,
	[FBillNo] [varchar](255) NOT NULL,
	[FTranType] [smallint] NOT NULL,
	[FStatus] [smallint] NOT NULL,
	[FMRP] [smallint] NOT NULL,
	[FType] [smallint] NULL,
	[FWorkShop] [int] NULL,
	[FItemID] [int] NOT NULL,
	[FQty] [decimal](28, 10) NOT NULL,
	[FCommitQty] [decimal](28, 10) NOT NULL,
	[FPlanCommitDate] [datetime] NULL,
	[FPlanFinishDate] [datetime] NULL,
	[FConveyerID] [int] NULL,
	[FCommitDate] [datetime] NULL,
	[FCheckerID] [int] NULL,
	[FCheckDate] [datetime] NULL,
	[FRequesterID] [int] NULL,
	[FBillerID] [int] NULL,
	[FSourceEntryID] [smallint] NOT NULL,
	[FClosed] [smallint] NOT NULL,
	[FNote] [varchar](255) NULL,
	[FUnitID] [int] NOT NULL,
	[FAuxCommitQty] [decimal](28, 10) NOT NULL,
	[FAuxQty] [decimal](28, 10) NOT NULL,
	[FOrderInterID] [int] NULL,
	[FPPOrderInterID] [int] NULL,
	[FParentInterID] [int] NULL,
	[FCancellation] [bit] NOT NULL,
	[FSupplyID] [int] NULL,
	[FQtyFinish] [decimal](28, 10) NOT NULL,
	[FQtyScrap] [decimal](28, 10) NOT NULL,
	[FQtyForItem] [decimal](28, 10) NULL,
	[FQtyLost] [decimal](28, 10) NOT NULL,
	[FPlanIssueDate] [datetime] NULL,
	[FRoutingID] [int] NULL,
	[FStartDate] [datetime] NULL,
	[FFinishDate] [datetime] NULL,
	[FAuxQtyFinish] [decimal](28, 10) NOT NULL,
	[FAuxQtyScrap] [decimal](28, 10) NOT NULL,
	[FAuxQtyForItem] [decimal](28, 10) NULL,
	[FAuxQtyLost] [decimal](28, 10) NOT NULL,
	[FMrpClosed] [int] NOT NULL,
	[FBomInterID] [int] NOT NULL,
	[FQtyPass] [decimal](28, 10) NOT NULL,
	[FAuxQtyPass] [decimal](28, 10) NOT NULL,
	[FQtyBack] [decimal](28, 10) NOT NULL,
	[FAuxQtyBack] [decimal](28, 10) NOT NULL,
	[FFinishTime] [decimal](28, 10) NOT NULL,
	[FReadyTIme] [decimal](28, 10) NOT NULL,
	[FPowerCutTime] [decimal](28, 10) NOT NULL,
	[FFixTime] [decimal](28, 10) NOT NULL,
	[FWaitItemTime] [decimal](28, 10) NOT NULL,
	[FWaitToolTime] [decimal](28, 10) NOT NULL,
	[FTaskID] [int] NOT NULL,
	[FWorkTypeID] [int] NOT NULL,
	[FCostObjID] [int] NOT NULL,
	[FStockQty] [decimal](28, 10) NOT NULL,
	[FAuxStockQty] [decimal](28, 10) NOT NULL,
	[FSuspend] [bit] NOT NULL,
	[FProjectNO] [int] NULL,
	[FMultiCheckLevel1] [int] NULL,
	[FMultiCheckLevel2] [int] NULL,
	[FMultiCheckLevel3] [int] NULL,
	[FMultiCheckLevel4] [int] NULL,
	[FMultiCheckLevel5] [int] NULL,
	[FMultiCheckLevel6] [int] NULL,
	[FMultiCheckDate1] [datetime] NULL,
	[FMultiCheckDate2] [datetime] NULL,
	[FMultiCheckDate3] [datetime] NULL,
	[FMultiCheckDate4] [datetime] NULL,
	[FMultiCheckDate5] [datetime] NULL,
	[FMultiCheckDate6] [datetime] NULL,
	[FCurCheckLevel] [int] NULL,
	[FProductionLineID] [int] NOT NULL,
	[FReleasedQty] [decimal](28, 10) NOT NULL,
	[FReleasedAuxQty] [decimal](28, 10) NOT NULL,
	[FUnScheduledQty] [decimal](28, 10) NOT NULL,
	[FUnScheduledAuxQty] [decimal](28, 10) NOT NULL,
	[FSubEntryID] [int] NOT NULL,
	[FScheduleID] [int] NOT NULL,
	[FPlanOrderInterID] [int] NOT NULL,
	[FProcessPrice] [decimal](28, 10) NOT NULL,
	[FProcessFee] [decimal](28, 10) NOT NULL,
	[FCustID] [int] NOT NULL,
	[FGMPBatchNo] [varchar](200) NOT NULL,
	[FGMPCollectRate] [decimal](28, 10) NOT NULL,
	[FGMPItemBalance] [decimal](28, 10) NOT NULL,
	[FGMPBulkQty] [decimal](28, 10) NOT NULL,
	[FCheckCommitQty] [decimal](28, 10) NOT NULL,
	[FAuxCheckCommitQty] [decimal](28, 10) NOT NULL,
	[FMRPLockFlag] [int] NOT NULL,
	[FHandworkClose] [int] NOT NULL,
	[FConfirmerID] [int] NULL,
	[FConfirmDate] [datetime] NULL,
	[FInHighLimit] [decimal](28, 10) NOT NULL,
	[FInHighLimitQty] [decimal](28, 10) NOT NULL,
	[FAuxInHighLimitQty] [decimal](28, 10) NOT NULL,
	[FInLowLimit] [decimal](28, 10) NOT NULL,
	[FInLowLimitQty] [decimal](28, 10) NOT NULL,
	[FAuxInLowLimitQty] [decimal](28, 10) NOT NULL,
	[FChangeTimes] [int] NOT NULL,
	[FCloseDate] [datetime] NULL,
	[FPlanConfirmed] [int] NOT NULL,
	[FPlanMode] [int] NOT NULL,
	[FMTONo] [nvarchar](50) NOT NULL,
	[FPrintCount] [int] NOT NULL,
	[FFinClosed] [int] NOT NULL,
	[FFinCloseer] [int] NULL,
	[FFinClosedate] [datetime] NULL,
	[FStockFlag] [int] NOT NULL,
	[FStartFlag] [int] NOT NULL,
	[FVchBillNo] [varchar](255) NOT NULL,
	[FVchInterID] [int] NOT NULL,
	[FCardClosed] [int] NOT NULL,
	[FHRReadyTime] [decimal](23, 10) NOT NULL,
	[FPlanCategory] [int] NOT NULL,
	[FBomCategory] [int] NOT NULL,
	[FSourceTranType] [int] NOT NULL,
	[FSourceInterId] [int] NOT NULL,
	[FSourceBillNo] [nvarchar](510) NOT NULL,
	[FReprocessedAuxQty] [decimal](23, 10) NOT NULL,
	[FReprocessedQty] [decimal](23, 10) NOT NULL,
	[FSelDiscardStockInAuxQty] [decimal](23, 10) NOT NULL,
	[FSelDiscardStockInQty] [decimal](23, 10) NOT NULL,
	[FDiscardStockInAuxQty] [decimal](23, 10) NOT NULL,
	[FDiscardStockInQty] [decimal](23, 10) NOT NULL,
	[FSampleBreakAuxQty] [decimal](23, 10) NOT NULL,
	[FSampleBreakQty] [decimal](23, 10) NOT NULL,
	[FHeadSelfJ0182] [varchar](255) NULL,
	[FResourceID] [bigint] NOT NULL,
	[FHeadSelfJ0181] [int] NULL,
	[FHeadSelfJ0185] [varchar](255) NULL,
	[FHeadSelfJ0187] [varchar](255) NULL,
	[FHeadSelfJ0189] [varchar](255) NULL,
	[FHeadSelfJ0191] [varchar](255) NULL,
	[FHeadSelfJ0193] [varchar](255) NULL,
	[FHeadSelfJ0195] [varchar](255) NULL,
	[FHeadSelfJ0197] [varchar](255) NULL,
	[FHeadSelfJ0199] [varchar](255) NULL,
	[FHeadSelfJ01101] [varchar](255) NULL,
	[FHeadSelfJ01104] [decimal](28, 10) NULL,
	[FHeadSelfJ01106] [decimal](28, 10) NULL,
	[FHeadSelfJ01108] [decimal](28, 10) NULL,
	[FHeadSelfJ01103] [int] NULL,
	[FHeadSelfJ01105] [int] NULL,
	[FHeadSelfJ01107] [int] NULL,
	[FHeadSelfJ0186] [varchar](255) NULL,
	[FHeadSelfJ0188] [varchar](255) NULL,
	[FHeadSelfJ0190] [varchar](255) NULL,
	[FHeadSelfJ0192] [varchar](255) NULL,
	[FHeadSelfJ0194] [varchar](255) NULL,
	[FHeadSelfJ0196] [varchar](255) NULL,
	[FHeadSelfJ0198] [varchar](255) NULL,
	[FHeadSelfJ01100] [varchar](255) NULL,
	[FHeadSelfJ01102] [varchar](255) NULL,
 CONSTRAINT [PK_ICMO] PRIMARY KEY CLUSTERED 
(
	[FInterID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FStatus__505BE5AD]  DEFAULT (0) FOR [FStatus]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FMRP__515009E6]  DEFAULT (0) FOR [FMRP]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FQty__52442E1F]  DEFAULT (0) FOR [FQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FCommitQty__53385258]  DEFAULT (0) FOR [FCommitQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FSourceEnt__71F1E3A2]  DEFAULT (0) FOR [FSourceEntryID]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FClosed]  DEFAULT (0) FOR [FClosed]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FNote]  DEFAULT ('') FOR [FNote]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FUnitID__4DD47EBD]  DEFAULT (0) FOR [FUnitID]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FAuxCommit__5EFF0ABF]  DEFAULT (0) FOR [FAuxCommitQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FAuxQty__5FF32EF8]  DEFAULT (0) FOR [FAuxQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FCancellat__34D3C6C9]  DEFAULT (0) FOR [FCancellation]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FQtyFinish__7227B923]  DEFAULT (0) FOR [FQtyFinish]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FQtyScrap__731BDD5C]  DEFAULT (0) FOR [FQtyScrap]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FQtyForItem]  DEFAULT (0) FOR [FQtyForItem]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FQtyLost__74100195]  DEFAULT (0) FOR [FQtyLost]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FRoutingID]  DEFAULT (0) FOR [FRoutingID]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FAuxQtyFin__750425CE]  DEFAULT (0) FOR [FAuxQtyFinish]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FAuxQtyScr__75F84A07]  DEFAULT (0) FOR [FAuxQtyScrap]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FAuxQtyForItem]  DEFAULT (0) FOR [FAuxQtyForItem]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FAuxQtyLos__76EC6E40]  DEFAULT (0) FOR [FAuxQtyLost]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FMrpClosed__2977EE0D]  DEFAULT (0) FOR [FMrpClosed]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FBomInterID]  DEFAULT (0) FOR [FBomInterID]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FQtyPass]  DEFAULT (0) FOR [FQtyPass]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FAuxQtyPass]  DEFAULT (0) FOR [FAuxQtyPass]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FQtyBack]  DEFAULT (0) FOR [FQtyBack]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FAuxQtyBack]  DEFAULT (0) FOR [FAuxQtyBack]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FFinishTime]  DEFAULT (0) FOR [FFinishTime]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FReadyTIme]  DEFAULT (0) FOR [FReadyTIme]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FPowerCutTime]  DEFAULT (0) FOR [FPowerCutTime]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FFixTime]  DEFAULT (0) FOR [FFixTime]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FWaitItemTime]  DEFAULT (0) FOR [FWaitItemTime]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FWaitToolTime]  DEFAULT (0) FOR [FWaitToolTime]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FProjectNO]  DEFAULT (0) FOR [FTaskID]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FWorkTypeID]  DEFAULT (0) FOR [FWorkTypeID]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FCostObject]  DEFAULT (0) FOR [FCostObjID]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FStockQty__461FE50A]  DEFAULT (0) FOR [FStockQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FAuxStockQ__47140943]  DEFAULT (0) FOR [FAuxStockQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FSuspend]  DEFAULT (0) FOR [FSuspend]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF__ICMO__FProjectNO__05F11CB3]  DEFAULT (0) FOR [FProjectNO]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FProductionLineID]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FReleasedQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FReleasedAuxQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FUnScheduledQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FUnScheduledAuxQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FSubEntryID]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FScheduleID]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FPlanOrderInterID]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FProcessPrice]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FProcessFee]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FCustID]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT ('') FOR [FGMPBatchNo]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FGMPCollectRate]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FGMPItemBalance]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FGMPBulkQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FCheckCommitQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FAuxCheckCommitQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FMRPLockFlag]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FHandworkClose]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FInHighLimit]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FInHighLimitQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FAuxInHighLimitQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FInLowLimit]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FInLowLimitQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FAuxInLowLimitQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FChangeTimes]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FPlanConfirmed]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (14036) FOR [FPlanMode]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT ('') FOR [FMTONo]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FPrintCount]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FFAClosed]  DEFAULT (0) FOR [FFinClosed]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FStockFlag]  DEFAULT (14215) FOR [FStockFlag]
GO

ALTER TABLE [dbo].[ICMO] ADD  CONSTRAINT [DF_ICMO_FStartFlag]  DEFAULT (0) FOR [FStartFlag]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT ('') FOR [FVchBillNo]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FVchInterID]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (1059) FOR [FCardClosed]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FHRReadyTime]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FPlanCategory]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FBomCategory]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FSourceTranType]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FSourceInterId]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT ('') FOR [FSourceBillNo]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FReprocessedAuxQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FReprocessedQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FSelDiscardStockInAuxQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FSelDiscardStockInQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FDiscardStockInAuxQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FDiscardStockInQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FSampleBreakAuxQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT (0) FOR [FSampleBreakQty]
GO

ALTER TABLE [dbo].[ICMO] ADD  DEFAULT ((0)) FOR [FResourceID]
GO


