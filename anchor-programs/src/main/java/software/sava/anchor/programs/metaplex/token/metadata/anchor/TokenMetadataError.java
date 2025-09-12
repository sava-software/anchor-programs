package software.sava.anchor.programs.metaplex.token.metadata.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface TokenMetadataError extends ProgramError permits
    TokenMetadataError.InstructionUnpackError,
    TokenMetadataError.InstructionPackError,
    TokenMetadataError.NotRentExempt,
    TokenMetadataError.AlreadyInitialized,
    TokenMetadataError.Uninitialized,
    TokenMetadataError.InvalidMetadataKey,
    TokenMetadataError.InvalidEditionKey,
    TokenMetadataError.UpdateAuthorityIncorrect,
    TokenMetadataError.UpdateAuthorityIsNotSigner,
    TokenMetadataError.NotMintAuthority,
    TokenMetadataError.InvalidMintAuthority,
    TokenMetadataError.NameTooLong,
    TokenMetadataError.SymbolTooLong,
    TokenMetadataError.UriTooLong,
    TokenMetadataError.UpdateAuthorityMustBeEqualToMetadataAuthorityAndSigner,
    TokenMetadataError.MintMismatch,
    TokenMetadataError.EditionsMustHaveExactlyOneToken,
    TokenMetadataError.MaxEditionsMintedAlready,
    TokenMetadataError.TokenMintToFailed,
    TokenMetadataError.MasterRecordMismatch,
    TokenMetadataError.DestinationMintMismatch,
    TokenMetadataError.EditionAlreadyMinted,
    TokenMetadataError.PrintingMintDecimalsShouldBeZero,
    TokenMetadataError.OneTimePrintingAuthorizationMintDecimalsShouldBeZero,
    TokenMetadataError.EditionMintDecimalsShouldBeZero,
    TokenMetadataError.TokenBurnFailed,
    TokenMetadataError.TokenAccountOneTimeAuthMintMismatch,
    TokenMetadataError.DerivedKeyInvalid,
    TokenMetadataError.PrintingMintMismatch,
    TokenMetadataError.OneTimePrintingAuthMintMismatch,
    TokenMetadataError.TokenAccountMintMismatch,
    TokenMetadataError.TokenAccountMintMismatchV2,
    TokenMetadataError.NotEnoughTokens,
    TokenMetadataError.PrintingMintAuthorizationAccountMismatch,
    TokenMetadataError.AuthorizationTokenAccountOwnerMismatch,
    TokenMetadataError.Disabled,
    TokenMetadataError.CreatorsTooLong,
    TokenMetadataError.CreatorsMustBeAtleastOne,
    TokenMetadataError.MustBeOneOfCreators,
    TokenMetadataError.NoCreatorsPresentOnMetadata,
    TokenMetadataError.CreatorNotFound,
    TokenMetadataError.InvalidBasisPoints,
    TokenMetadataError.PrimarySaleCanOnlyBeFlippedToTrue,
    TokenMetadataError.OwnerMismatch,
    TokenMetadataError.NoBalanceInAccountForAuthorization,
    TokenMetadataError.ShareTotalMustBe100,
    TokenMetadataError.ReservationExists,
    TokenMetadataError.ReservationDoesNotExist,
    TokenMetadataError.ReservationNotSet,
    TokenMetadataError.ReservationAlreadyMade,
    TokenMetadataError.BeyondMaxAddressSize,
    TokenMetadataError.NumericalOverflowError,
    TokenMetadataError.ReservationBreachesMaximumSupply,
    TokenMetadataError.AddressNotInReservation,
    TokenMetadataError.CannotVerifyAnotherCreator,
    TokenMetadataError.CannotUnverifyAnotherCreator,
    TokenMetadataError.SpotMismatch,
    TokenMetadataError.IncorrectOwner,
    TokenMetadataError.PrintingWouldBreachMaximumSupply,
    TokenMetadataError.DataIsImmutable,
    TokenMetadataError.DuplicateCreatorAddress,
    TokenMetadataError.ReservationSpotsRemainingShouldMatchTotalSpotsAtStart,
    TokenMetadataError.InvalidTokenProgram,
    TokenMetadataError.DataTypeMismatch,
    TokenMetadataError.BeyondAlottedAddressSize,
    TokenMetadataError.ReservationNotComplete,
    TokenMetadataError.TriedToReplaceAnExistingReservation,
    TokenMetadataError.InvalidOperation,
    TokenMetadataError.InvalidOwner,
    TokenMetadataError.PrintingMintSupplyMustBeZeroForConversion,
    TokenMetadataError.OneTimeAuthMintSupplyMustBeZeroForConversion,
    TokenMetadataError.InvalidEditionIndex,
    TokenMetadataError.ReservationArrayShouldBeSizeOne,
    TokenMetadataError.IsMutableCanOnlyBeFlippedToFalse,
    TokenMetadataError.CollectionCannotBeVerifiedInThisInstruction,
    TokenMetadataError.Removed,
    TokenMetadataError.MustBeBurned,
    TokenMetadataError.InvalidUseMethod,
    TokenMetadataError.CannotChangeUseMethodAfterFirstUse,
    TokenMetadataError.CannotChangeUsesAfterFirstUse,
    TokenMetadataError.CollectionNotFound,
    TokenMetadataError.InvalidCollectionUpdateAuthority,
    TokenMetadataError.CollectionMustBeAUniqueMasterEdition,
    TokenMetadataError.UseAuthorityRecordAlreadyExists,
    TokenMetadataError.UseAuthorityRecordAlreadyRevoked,
    TokenMetadataError.Unusable,
    TokenMetadataError.NotEnoughUses,
    TokenMetadataError.CollectionAuthorityRecordAlreadyExists,
    TokenMetadataError.CollectionAuthorityDoesNotExist,
    TokenMetadataError.InvalidUseAuthorityRecord,
    TokenMetadataError.InvalidCollectionAuthorityRecord,
    TokenMetadataError.InvalidFreezeAuthority,
    TokenMetadataError.InvalidDelegate,
    TokenMetadataError.CannotAdjustVerifiedCreator,
    TokenMetadataError.CannotRemoveVerifiedCreator,
    TokenMetadataError.CannotWipeVerifiedCreators,
    TokenMetadataError.NotAllowedToChangeSellerFeeBasisPoints,
    TokenMetadataError.EditionOverrideCannotBeZero,
    TokenMetadataError.InvalidUser,
    TokenMetadataError.RevokeCollectionAuthoritySignerIncorrect,
    TokenMetadataError.TokenCloseFailed,
    TokenMetadataError.UnsizedCollection,
    TokenMetadataError.SizedCollection,
    TokenMetadataError.MissingCollectionMetadata,
    TokenMetadataError.NotAMemberOfCollection,
    TokenMetadataError.NotVerifiedMemberOfCollection,
    TokenMetadataError.NotACollectionParent,
    TokenMetadataError.CouldNotDetermineTokenStandard,
    TokenMetadataError.MissingEditionAccount,
    TokenMetadataError.NotAMasterEdition,
    TokenMetadataError.MasterEditionHasPrints,
    TokenMetadataError.BorshDeserializationError,
    TokenMetadataError.CannotUpdateVerifiedCollection,
    TokenMetadataError.CollectionMasterEditionAccountInvalid,
    TokenMetadataError.AlreadyVerified,
    TokenMetadataError.AlreadyUnverified,
    TokenMetadataError.NotAPrintEdition,
    TokenMetadataError.InvalidMasterEdition,
    TokenMetadataError.InvalidPrintEdition,
    TokenMetadataError.InvalidEditionMarker,
    TokenMetadataError.ReservationListDeprecated,
    TokenMetadataError.PrintEditionDoesNotMatchMasterEdition,
    TokenMetadataError.EditionNumberGreaterThanMaxSupply,
    TokenMetadataError.MustUnverify,
    TokenMetadataError.InvalidEscrowBumpSeed,
    TokenMetadataError.MustBeEscrowAuthority,
    TokenMetadataError.InvalidSystemProgram,
    TokenMetadataError.MustBeNonFungible,
    TokenMetadataError.InsufficientTokens,
    TokenMetadataError.BorshSerializationError,
    TokenMetadataError.NoFreezeAuthoritySet,
    TokenMetadataError.InvalidCollectionSizeChange,
    TokenMetadataError.InvalidBubblegumSigner,
    TokenMetadataError.EscrowParentHasDelegate,
    TokenMetadataError.MintIsNotSigner,
    TokenMetadataError.InvalidTokenStandard,
    TokenMetadataError.InvalidMintForTokenStandard,
    TokenMetadataError.InvalidAuthorizationRules,
    TokenMetadataError.MissingAuthorizationRules,
    TokenMetadataError.MissingProgrammableConfig,
    TokenMetadataError.InvalidProgrammableConfig,
    TokenMetadataError.DelegateAlreadyExists,
    TokenMetadataError.DelegateNotFound,
    TokenMetadataError.MissingAccountInBuilder,
    TokenMetadataError.MissingArgumentInBuilder,
    TokenMetadataError.FeatureNotSupported,
    TokenMetadataError.InvalidSystemWallet,
    TokenMetadataError.OnlySaleDelegateCanTransfer,
    TokenMetadataError.MissingTokenAccount,
    TokenMetadataError.MissingSplTokenProgram,
    TokenMetadataError.MissingAuthorizationRulesProgram,
    TokenMetadataError.InvalidDelegateRoleForTransfer,
    TokenMetadataError.InvalidTransferAuthority,
    TokenMetadataError.InstructionNotSupported,
    TokenMetadataError.KeyMismatch,
    TokenMetadataError.LockedToken,
    TokenMetadataError.UnlockedToken,
    TokenMetadataError.MissingDelegateRole,
    TokenMetadataError.InvalidAuthorityType,
    TokenMetadataError.MissingTokenRecord,
    TokenMetadataError.MintSupplyMustBeZero,
    TokenMetadataError.DataIsEmptyOrZeroed,
    TokenMetadataError.MissingTokenOwnerAccount,
    TokenMetadataError.InvalidMasterEditionAccountLength,
    TokenMetadataError.IncorrectTokenState,
    TokenMetadataError.InvalidDelegateRole,
    TokenMetadataError.MissingPrintSupply,
    TokenMetadataError.MissingMasterEditionAccount,
    TokenMetadataError.AmountMustBeGreaterThanZero,
    TokenMetadataError.InvalidDelegateArgs,
    TokenMetadataError.MissingLockedTransferAddress,
    TokenMetadataError.InvalidLockedTransferAddress,
    TokenMetadataError.DataIncrementLimitExceeded,
    TokenMetadataError.CannotUpdateAssetWithDelegate,
    TokenMetadataError.InvalidAmount,
    TokenMetadataError.MissingMasterEditionMintAccount,
    TokenMetadataError.MissingMasterEditionTokenAccount,
    TokenMetadataError.MissingEditionMarkerAccount,
    TokenMetadataError.CannotBurnWithDelegate,
    TokenMetadataError.MissingEdition,
    TokenMetadataError.InvalidAssociatedTokenAccountProgram,
    TokenMetadataError.InvalidInstructionsSysvar,
    TokenMetadataError.InvalidParentAccounts,
    TokenMetadataError.InvalidUpdateArgs,
    TokenMetadataError.InsufficientTokenBalance,
    TokenMetadataError.MissingCollectionMint,
    TokenMetadataError.MissingCollectionMasterEdition,
    TokenMetadataError.InvalidTokenRecord,
    TokenMetadataError.InvalidCloseAuthority,
    TokenMetadataError.InvalidInstruction,
    TokenMetadataError.MissingDelegateRecord,
    TokenMetadataError.InvalidFeeAccount,
    TokenMetadataError.InvalidMetadataFlags,
    TokenMetadataError.CannotChangeUpdateAuthorityWithDelegate,
    TokenMetadataError.InvalidMintExtensionType,
    TokenMetadataError.InvalidMintCloseAuthority,
    TokenMetadataError.InvalidMetadataPointer,
    TokenMetadataError.InvalidTokenExtensionType,
    TokenMetadataError.MissingImmutableOwnerExtension,
    TokenMetadataError.ExpectedUninitializedAccount,
    TokenMetadataError.InvalidEditionAccountLength,
    TokenMetadataError.AccountAlreadyResized,
    TokenMetadataError.ConditionsForClosingNotMet {

  static TokenMetadataError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 0 -> InstructionUnpackError.INSTANCE;
      case 1 -> InstructionPackError.INSTANCE;
      case 2 -> NotRentExempt.INSTANCE;
      case 3 -> AlreadyInitialized.INSTANCE;
      case 4 -> Uninitialized.INSTANCE;
      case 5 -> InvalidMetadataKey.INSTANCE;
      case 6 -> InvalidEditionKey.INSTANCE;
      case 7 -> UpdateAuthorityIncorrect.INSTANCE;
      case 8 -> UpdateAuthorityIsNotSigner.INSTANCE;
      case 9 -> NotMintAuthority.INSTANCE;
      case 10 -> InvalidMintAuthority.INSTANCE;
      case 11 -> NameTooLong.INSTANCE;
      case 12 -> SymbolTooLong.INSTANCE;
      case 13 -> UriTooLong.INSTANCE;
      case 14 -> UpdateAuthorityMustBeEqualToMetadataAuthorityAndSigner.INSTANCE;
      case 15 -> MintMismatch.INSTANCE;
      case 16 -> EditionsMustHaveExactlyOneToken.INSTANCE;
      case 17 -> MaxEditionsMintedAlready.INSTANCE;
      case 18 -> TokenMintToFailed.INSTANCE;
      case 19 -> MasterRecordMismatch.INSTANCE;
      case 20 -> DestinationMintMismatch.INSTANCE;
      case 21 -> EditionAlreadyMinted.INSTANCE;
      case 22 -> PrintingMintDecimalsShouldBeZero.INSTANCE;
      case 23 -> OneTimePrintingAuthorizationMintDecimalsShouldBeZero.INSTANCE;
      case 24 -> EditionMintDecimalsShouldBeZero.INSTANCE;
      case 25 -> TokenBurnFailed.INSTANCE;
      case 26 -> TokenAccountOneTimeAuthMintMismatch.INSTANCE;
      case 27 -> DerivedKeyInvalid.INSTANCE;
      case 28 -> PrintingMintMismatch.INSTANCE;
      case 29 -> OneTimePrintingAuthMintMismatch.INSTANCE;
      case 30 -> TokenAccountMintMismatch.INSTANCE;
      case 31 -> TokenAccountMintMismatchV2.INSTANCE;
      case 32 -> NotEnoughTokens.INSTANCE;
      case 33 -> PrintingMintAuthorizationAccountMismatch.INSTANCE;
      case 34 -> AuthorizationTokenAccountOwnerMismatch.INSTANCE;
      case 35 -> Disabled.INSTANCE;
      case 36 -> CreatorsTooLong.INSTANCE;
      case 37 -> CreatorsMustBeAtleastOne.INSTANCE;
      case 38 -> MustBeOneOfCreators.INSTANCE;
      case 39 -> NoCreatorsPresentOnMetadata.INSTANCE;
      case 40 -> CreatorNotFound.INSTANCE;
      case 41 -> InvalidBasisPoints.INSTANCE;
      case 42 -> PrimarySaleCanOnlyBeFlippedToTrue.INSTANCE;
      case 43 -> OwnerMismatch.INSTANCE;
      case 44 -> NoBalanceInAccountForAuthorization.INSTANCE;
      case 45 -> ShareTotalMustBe100.INSTANCE;
      case 46 -> ReservationExists.INSTANCE;
      case 47 -> ReservationDoesNotExist.INSTANCE;
      case 48 -> ReservationNotSet.INSTANCE;
      case 49 -> ReservationAlreadyMade.INSTANCE;
      case 50 -> BeyondMaxAddressSize.INSTANCE;
      case 51 -> NumericalOverflowError.INSTANCE;
      case 52 -> ReservationBreachesMaximumSupply.INSTANCE;
      case 53 -> AddressNotInReservation.INSTANCE;
      case 54 -> CannotVerifyAnotherCreator.INSTANCE;
      case 55 -> CannotUnverifyAnotherCreator.INSTANCE;
      case 56 -> SpotMismatch.INSTANCE;
      case 57 -> IncorrectOwner.INSTANCE;
      case 58 -> PrintingWouldBreachMaximumSupply.INSTANCE;
      case 59 -> DataIsImmutable.INSTANCE;
      case 60 -> DuplicateCreatorAddress.INSTANCE;
      case 61 -> ReservationSpotsRemainingShouldMatchTotalSpotsAtStart.INSTANCE;
      case 62 -> InvalidTokenProgram.INSTANCE;
      case 63 -> DataTypeMismatch.INSTANCE;
      case 64 -> BeyondAlottedAddressSize.INSTANCE;
      case 65 -> ReservationNotComplete.INSTANCE;
      case 66 -> TriedToReplaceAnExistingReservation.INSTANCE;
      case 67 -> InvalidOperation.INSTANCE;
      case 68 -> InvalidOwner.INSTANCE;
      case 69 -> PrintingMintSupplyMustBeZeroForConversion.INSTANCE;
      case 70 -> OneTimeAuthMintSupplyMustBeZeroForConversion.INSTANCE;
      case 71 -> InvalidEditionIndex.INSTANCE;
      case 72 -> ReservationArrayShouldBeSizeOne.INSTANCE;
      case 73 -> IsMutableCanOnlyBeFlippedToFalse.INSTANCE;
      case 74 -> CollectionCannotBeVerifiedInThisInstruction.INSTANCE;
      case 75 -> Removed.INSTANCE;
      case 76 -> MustBeBurned.INSTANCE;
      case 77 -> InvalidUseMethod.INSTANCE;
      case 78 -> CannotChangeUseMethodAfterFirstUse.INSTANCE;
      case 79 -> CannotChangeUsesAfterFirstUse.INSTANCE;
      case 80 -> CollectionNotFound.INSTANCE;
      case 81 -> InvalidCollectionUpdateAuthority.INSTANCE;
      case 82 -> CollectionMustBeAUniqueMasterEdition.INSTANCE;
      case 83 -> UseAuthorityRecordAlreadyExists.INSTANCE;
      case 84 -> UseAuthorityRecordAlreadyRevoked.INSTANCE;
      case 85 -> Unusable.INSTANCE;
      case 86 -> NotEnoughUses.INSTANCE;
      case 87 -> CollectionAuthorityRecordAlreadyExists.INSTANCE;
      case 88 -> CollectionAuthorityDoesNotExist.INSTANCE;
      case 89 -> InvalidUseAuthorityRecord.INSTANCE;
      case 90 -> InvalidCollectionAuthorityRecord.INSTANCE;
      case 91 -> InvalidFreezeAuthority.INSTANCE;
      case 92 -> InvalidDelegate.INSTANCE;
      case 93 -> CannotAdjustVerifiedCreator.INSTANCE;
      case 94 -> CannotRemoveVerifiedCreator.INSTANCE;
      case 95 -> CannotWipeVerifiedCreators.INSTANCE;
      case 96 -> NotAllowedToChangeSellerFeeBasisPoints.INSTANCE;
      case 97 -> EditionOverrideCannotBeZero.INSTANCE;
      case 98 -> InvalidUser.INSTANCE;
      case 99 -> RevokeCollectionAuthoritySignerIncorrect.INSTANCE;
      case 100 -> TokenCloseFailed.INSTANCE;
      case 101 -> UnsizedCollection.INSTANCE;
      case 102 -> SizedCollection.INSTANCE;
      case 103 -> MissingCollectionMetadata.INSTANCE;
      case 104 -> NotAMemberOfCollection.INSTANCE;
      case 105 -> NotVerifiedMemberOfCollection.INSTANCE;
      case 106 -> NotACollectionParent.INSTANCE;
      case 107 -> CouldNotDetermineTokenStandard.INSTANCE;
      case 108 -> MissingEditionAccount.INSTANCE;
      case 109 -> NotAMasterEdition.INSTANCE;
      case 110 -> MasterEditionHasPrints.INSTANCE;
      case 111 -> BorshDeserializationError.INSTANCE;
      case 112 -> CannotUpdateVerifiedCollection.INSTANCE;
      case 113 -> CollectionMasterEditionAccountInvalid.INSTANCE;
      case 114 -> AlreadyVerified.INSTANCE;
      case 115 -> AlreadyUnverified.INSTANCE;
      case 116 -> NotAPrintEdition.INSTANCE;
      case 117 -> InvalidMasterEdition.INSTANCE;
      case 118 -> InvalidPrintEdition.INSTANCE;
      case 119 -> InvalidEditionMarker.INSTANCE;
      case 120 -> ReservationListDeprecated.INSTANCE;
      case 121 -> PrintEditionDoesNotMatchMasterEdition.INSTANCE;
      case 122 -> EditionNumberGreaterThanMaxSupply.INSTANCE;
      case 123 -> MustUnverify.INSTANCE;
      case 124 -> InvalidEscrowBumpSeed.INSTANCE;
      case 125 -> MustBeEscrowAuthority.INSTANCE;
      case 126 -> InvalidSystemProgram.INSTANCE;
      case 127 -> MustBeNonFungible.INSTANCE;
      case 128 -> InsufficientTokens.INSTANCE;
      case 129 -> BorshSerializationError.INSTANCE;
      case 130 -> NoFreezeAuthoritySet.INSTANCE;
      case 131 -> InvalidCollectionSizeChange.INSTANCE;
      case 132 -> InvalidBubblegumSigner.INSTANCE;
      case 133 -> EscrowParentHasDelegate.INSTANCE;
      case 134 -> MintIsNotSigner.INSTANCE;
      case 135 -> InvalidTokenStandard.INSTANCE;
      case 136 -> InvalidMintForTokenStandard.INSTANCE;
      case 137 -> InvalidAuthorizationRules.INSTANCE;
      case 138 -> MissingAuthorizationRules.INSTANCE;
      case 139 -> MissingProgrammableConfig.INSTANCE;
      case 140 -> InvalidProgrammableConfig.INSTANCE;
      case 141 -> DelegateAlreadyExists.INSTANCE;
      case 142 -> DelegateNotFound.INSTANCE;
      case 143 -> MissingAccountInBuilder.INSTANCE;
      case 144 -> MissingArgumentInBuilder.INSTANCE;
      case 145 -> FeatureNotSupported.INSTANCE;
      case 146 -> InvalidSystemWallet.INSTANCE;
      case 147 -> OnlySaleDelegateCanTransfer.INSTANCE;
      case 148 -> MissingTokenAccount.INSTANCE;
      case 149 -> MissingSplTokenProgram.INSTANCE;
      case 150 -> MissingAuthorizationRulesProgram.INSTANCE;
      case 151 -> InvalidDelegateRoleForTransfer.INSTANCE;
      case 152 -> InvalidTransferAuthority.INSTANCE;
      case 153 -> InstructionNotSupported.INSTANCE;
      case 154 -> KeyMismatch.INSTANCE;
      case 155 -> LockedToken.INSTANCE;
      case 156 -> UnlockedToken.INSTANCE;
      case 157 -> MissingDelegateRole.INSTANCE;
      case 158 -> InvalidAuthorityType.INSTANCE;
      case 159 -> MissingTokenRecord.INSTANCE;
      case 160 -> MintSupplyMustBeZero.INSTANCE;
      case 161 -> DataIsEmptyOrZeroed.INSTANCE;
      case 162 -> MissingTokenOwnerAccount.INSTANCE;
      case 163 -> InvalidMasterEditionAccountLength.INSTANCE;
      case 164 -> IncorrectTokenState.INSTANCE;
      case 165 -> InvalidDelegateRole.INSTANCE;
      case 166 -> MissingPrintSupply.INSTANCE;
      case 167 -> MissingMasterEditionAccount.INSTANCE;
      case 168 -> AmountMustBeGreaterThanZero.INSTANCE;
      case 169 -> InvalidDelegateArgs.INSTANCE;
      case 170 -> MissingLockedTransferAddress.INSTANCE;
      case 171 -> InvalidLockedTransferAddress.INSTANCE;
      case 172 -> DataIncrementLimitExceeded.INSTANCE;
      case 173 -> CannotUpdateAssetWithDelegate.INSTANCE;
      case 174 -> InvalidAmount.INSTANCE;
      case 175 -> MissingMasterEditionMintAccount.INSTANCE;
      case 176 -> MissingMasterEditionTokenAccount.INSTANCE;
      case 177 -> MissingEditionMarkerAccount.INSTANCE;
      case 178 -> CannotBurnWithDelegate.INSTANCE;
      case 179 -> MissingEdition.INSTANCE;
      case 180 -> InvalidAssociatedTokenAccountProgram.INSTANCE;
      case 181 -> InvalidInstructionsSysvar.INSTANCE;
      case 182 -> InvalidParentAccounts.INSTANCE;
      case 183 -> InvalidUpdateArgs.INSTANCE;
      case 184 -> InsufficientTokenBalance.INSTANCE;
      case 185 -> MissingCollectionMint.INSTANCE;
      case 186 -> MissingCollectionMasterEdition.INSTANCE;
      case 187 -> InvalidTokenRecord.INSTANCE;
      case 188 -> InvalidCloseAuthority.INSTANCE;
      case 189 -> InvalidInstruction.INSTANCE;
      case 190 -> MissingDelegateRecord.INSTANCE;
      case 191 -> InvalidFeeAccount.INSTANCE;
      case 192 -> InvalidMetadataFlags.INSTANCE;
      case 193 -> CannotChangeUpdateAuthorityWithDelegate.INSTANCE;
      case 194 -> InvalidMintExtensionType.INSTANCE;
      case 195 -> InvalidMintCloseAuthority.INSTANCE;
      case 196 -> InvalidMetadataPointer.INSTANCE;
      case 197 -> InvalidTokenExtensionType.INSTANCE;
      case 198 -> MissingImmutableOwnerExtension.INSTANCE;
      case 199 -> ExpectedUninitializedAccount.INSTANCE;
      case 200 -> InvalidEditionAccountLength.INSTANCE;
      case 201 -> AccountAlreadyResized.INSTANCE;
      case 202 -> ConditionsForClosingNotMet.INSTANCE;
      default -> throw new IllegalStateException("Unexpected TokenMetadata error code: " + errorCode);
    };
  }

  record InstructionUnpackError(int code, String msg) implements TokenMetadataError {

    public static final InstructionUnpackError INSTANCE = new InstructionUnpackError(
        0, ""
    );
  }

  record InstructionPackError(int code, String msg) implements TokenMetadataError {

    public static final InstructionPackError INSTANCE = new InstructionPackError(
        1, ""
    );
  }

  record NotRentExempt(int code, String msg) implements TokenMetadataError {

    public static final NotRentExempt INSTANCE = new NotRentExempt(
        2, "Lamport balance below rent-exempt threshold"
    );
  }

  record AlreadyInitialized(int code, String msg) implements TokenMetadataError {

    public static final AlreadyInitialized INSTANCE = new AlreadyInitialized(
        3, "Already initialized"
    );
  }

  record Uninitialized(int code, String msg) implements TokenMetadataError {

    public static final Uninitialized INSTANCE = new Uninitialized(
        4, "Uninitialized"
    );
  }

  record InvalidMetadataKey(int code, String msg) implements TokenMetadataError {

    public static final InvalidMetadataKey INSTANCE = new InvalidMetadataKey(
        5, " Metadata's key must match seed of ['metadata', program id, mint] provided"
    );
  }

  record InvalidEditionKey(int code, String msg) implements TokenMetadataError {

    public static final InvalidEditionKey INSTANCE = new InvalidEditionKey(
        6, "Edition's key must match seed of ['metadata', program id, name, 'edition'] provided"
    );
  }

  record UpdateAuthorityIncorrect(int code, String msg) implements TokenMetadataError {

    public static final UpdateAuthorityIncorrect INSTANCE = new UpdateAuthorityIncorrect(
        7, "Update Authority given does not match"
    );
  }

  record UpdateAuthorityIsNotSigner(int code, String msg) implements TokenMetadataError {

    public static final UpdateAuthorityIsNotSigner INSTANCE = new UpdateAuthorityIsNotSigner(
        8, "Update Authority needs to be signer to update metadata"
    );
  }

  record NotMintAuthority(int code, String msg) implements TokenMetadataError {

    public static final NotMintAuthority INSTANCE = new NotMintAuthority(
        9, "You must be the mint authority and signer on this transaction"
    );
  }

  record InvalidMintAuthority(int code, String msg) implements TokenMetadataError {

    public static final InvalidMintAuthority INSTANCE = new InvalidMintAuthority(
        10, "Mint authority provided does not match the authority on the mint"
    );
  }

  record NameTooLong(int code, String msg) implements TokenMetadataError {

    public static final NameTooLong INSTANCE = new NameTooLong(
        11, "Name too long"
    );
  }

  record SymbolTooLong(int code, String msg) implements TokenMetadataError {

    public static final SymbolTooLong INSTANCE = new SymbolTooLong(
        12, "Symbol too long"
    );
  }

  record UriTooLong(int code, String msg) implements TokenMetadataError {

    public static final UriTooLong INSTANCE = new UriTooLong(
        13, "URI too long"
    );
  }

  record UpdateAuthorityMustBeEqualToMetadataAuthorityAndSigner(int code, String msg) implements TokenMetadataError {

    public static final UpdateAuthorityMustBeEqualToMetadataAuthorityAndSigner INSTANCE = new UpdateAuthorityMustBeEqualToMetadataAuthorityAndSigner(
        14, ""
    );
  }

  record MintMismatch(int code, String msg) implements TokenMetadataError {

    public static final MintMismatch INSTANCE = new MintMismatch(
        15, "Mint given does not match mint on Metadata"
    );
  }

  record EditionsMustHaveExactlyOneToken(int code, String msg) implements TokenMetadataError {

    public static final EditionsMustHaveExactlyOneToken INSTANCE = new EditionsMustHaveExactlyOneToken(
        16, "Editions must have exactly one token"
    );
  }

  record MaxEditionsMintedAlready(int code, String msg) implements TokenMetadataError {

    public static final MaxEditionsMintedAlready INSTANCE = new MaxEditionsMintedAlready(
        17, ""
    );
  }

  record TokenMintToFailed(int code, String msg) implements TokenMetadataError {

    public static final TokenMintToFailed INSTANCE = new TokenMintToFailed(
        18, ""
    );
  }

  record MasterRecordMismatch(int code, String msg) implements TokenMetadataError {

    public static final MasterRecordMismatch INSTANCE = new MasterRecordMismatch(
        19, ""
    );
  }

  record DestinationMintMismatch(int code, String msg) implements TokenMetadataError {

    public static final DestinationMintMismatch INSTANCE = new DestinationMintMismatch(
        20, ""
    );
  }

  record EditionAlreadyMinted(int code, String msg) implements TokenMetadataError {

    public static final EditionAlreadyMinted INSTANCE = new EditionAlreadyMinted(
        21, ""
    );
  }

  record PrintingMintDecimalsShouldBeZero(int code, String msg) implements TokenMetadataError {

    public static final PrintingMintDecimalsShouldBeZero INSTANCE = new PrintingMintDecimalsShouldBeZero(
        22, ""
    );
  }

  record OneTimePrintingAuthorizationMintDecimalsShouldBeZero(int code, String msg) implements TokenMetadataError {

    public static final OneTimePrintingAuthorizationMintDecimalsShouldBeZero INSTANCE = new OneTimePrintingAuthorizationMintDecimalsShouldBeZero(
        23, ""
    );
  }

  record EditionMintDecimalsShouldBeZero(int code, String msg) implements TokenMetadataError {

    public static final EditionMintDecimalsShouldBeZero INSTANCE = new EditionMintDecimalsShouldBeZero(
        24, "EditionMintDecimalsShouldBeZero"
    );
  }

  record TokenBurnFailed(int code, String msg) implements TokenMetadataError {

    public static final TokenBurnFailed INSTANCE = new TokenBurnFailed(
        25, ""
    );
  }

  record TokenAccountOneTimeAuthMintMismatch(int code, String msg) implements TokenMetadataError {

    public static final TokenAccountOneTimeAuthMintMismatch INSTANCE = new TokenAccountOneTimeAuthMintMismatch(
        26, ""
    );
  }

  record DerivedKeyInvalid(int code, String msg) implements TokenMetadataError {

    public static final DerivedKeyInvalid INSTANCE = new DerivedKeyInvalid(
        27, "Derived key invalid"
    );
  }

  record PrintingMintMismatch(int code, String msg) implements TokenMetadataError {

    public static final PrintingMintMismatch INSTANCE = new PrintingMintMismatch(
        28, "The Printing mint does not match that on the master edition!"
    );
  }

  record OneTimePrintingAuthMintMismatch(int code, String msg) implements TokenMetadataError {

    public static final OneTimePrintingAuthMintMismatch INSTANCE = new OneTimePrintingAuthMintMismatch(
        29, "The One Time Printing Auth mint does not match that on the master edition!"
    );
  }

  record TokenAccountMintMismatch(int code, String msg) implements TokenMetadataError {

    public static final TokenAccountMintMismatch INSTANCE = new TokenAccountMintMismatch(
        30, "The mint of the token account does not match the Printing mint!"
    );
  }

  record TokenAccountMintMismatchV2(int code, String msg) implements TokenMetadataError {

    public static final TokenAccountMintMismatchV2 INSTANCE = new TokenAccountMintMismatchV2(
        31, "The mint of the token account does not match the master metadata mint!"
    );
  }

  record NotEnoughTokens(int code, String msg) implements TokenMetadataError {

    public static final NotEnoughTokens INSTANCE = new NotEnoughTokens(
        32, "Not enough tokens to mint a limited edition"
    );
  }

  record PrintingMintAuthorizationAccountMismatch(int code, String msg) implements TokenMetadataError {

    public static final PrintingMintAuthorizationAccountMismatch INSTANCE = new PrintingMintAuthorizationAccountMismatch(
        33, ""
    );
  }

  record AuthorizationTokenAccountOwnerMismatch(int code, String msg) implements TokenMetadataError {

    public static final AuthorizationTokenAccountOwnerMismatch INSTANCE = new AuthorizationTokenAccountOwnerMismatch(
        34, ""
    );
  }

  record Disabled(int code, String msg) implements TokenMetadataError {

    public static final Disabled INSTANCE = new Disabled(
        35, ""
    );
  }

  record CreatorsTooLong(int code, String msg) implements TokenMetadataError {

    public static final CreatorsTooLong INSTANCE = new CreatorsTooLong(
        36, "Creators list too long"
    );
  }

  record CreatorsMustBeAtleastOne(int code, String msg) implements TokenMetadataError {

    public static final CreatorsMustBeAtleastOne INSTANCE = new CreatorsMustBeAtleastOne(
        37, "Creators must be at least one if set"
    );
  }

  record MustBeOneOfCreators(int code, String msg) implements TokenMetadataError {

    public static final MustBeOneOfCreators INSTANCE = new MustBeOneOfCreators(
        38, ""
    );
  }

  record NoCreatorsPresentOnMetadata(int code, String msg) implements TokenMetadataError {

    public static final NoCreatorsPresentOnMetadata INSTANCE = new NoCreatorsPresentOnMetadata(
        39, "This metadata does not have creators"
    );
  }

  record CreatorNotFound(int code, String msg) implements TokenMetadataError {

    public static final CreatorNotFound INSTANCE = new CreatorNotFound(
        40, "This creator address was not found"
    );
  }

  record InvalidBasisPoints(int code, String msg) implements TokenMetadataError {

    public static final InvalidBasisPoints INSTANCE = new InvalidBasisPoints(
        41, "Basis points cannot be more than 10000"
    );
  }

  record PrimarySaleCanOnlyBeFlippedToTrue(int code, String msg) implements TokenMetadataError {

    public static final PrimarySaleCanOnlyBeFlippedToTrue INSTANCE = new PrimarySaleCanOnlyBeFlippedToTrue(
        42, "Primary sale can only be flipped to true and is immutable"
    );
  }

  record OwnerMismatch(int code, String msg) implements TokenMetadataError {

    public static final OwnerMismatch INSTANCE = new OwnerMismatch(
        43, "Owner does not match that on the account given"
    );
  }

  record NoBalanceInAccountForAuthorization(int code, String msg) implements TokenMetadataError {

    public static final NoBalanceInAccountForAuthorization INSTANCE = new NoBalanceInAccountForAuthorization(
        44, "This account has no tokens to be used for authorization"
    );
  }

  record ShareTotalMustBe100(int code, String msg) implements TokenMetadataError {

    public static final ShareTotalMustBe100 INSTANCE = new ShareTotalMustBe100(
        45, "Share total must equal 100 for creator array"
    );
  }

  record ReservationExists(int code, String msg) implements TokenMetadataError {

    public static final ReservationExists INSTANCE = new ReservationExists(
        46, ""
    );
  }

  record ReservationDoesNotExist(int code, String msg) implements TokenMetadataError {

    public static final ReservationDoesNotExist INSTANCE = new ReservationDoesNotExist(
        47, ""
    );
  }

  record ReservationNotSet(int code, String msg) implements TokenMetadataError {

    public static final ReservationNotSet INSTANCE = new ReservationNotSet(
        48, ""
    );
  }

  record ReservationAlreadyMade(int code, String msg) implements TokenMetadataError {

    public static final ReservationAlreadyMade INSTANCE = new ReservationAlreadyMade(
        49, ""
    );
  }

  record BeyondMaxAddressSize(int code, String msg) implements TokenMetadataError {

    public static final BeyondMaxAddressSize INSTANCE = new BeyondMaxAddressSize(
        50, ""
    );
  }

  record NumericalOverflowError(int code, String msg) implements TokenMetadataError {

    public static final NumericalOverflowError INSTANCE = new NumericalOverflowError(
        51, "NumericalOverflowError"
    );
  }

  record ReservationBreachesMaximumSupply(int code, String msg) implements TokenMetadataError {

    public static final ReservationBreachesMaximumSupply INSTANCE = new ReservationBreachesMaximumSupply(
        52, ""
    );
  }

  record AddressNotInReservation(int code, String msg) implements TokenMetadataError {

    public static final AddressNotInReservation INSTANCE = new AddressNotInReservation(
        53, ""
    );
  }

  record CannotVerifyAnotherCreator(int code, String msg) implements TokenMetadataError {

    public static final CannotVerifyAnotherCreator INSTANCE = new CannotVerifyAnotherCreator(
        54, "You cannot unilaterally verify another creator, they must sign"
    );
  }

  record CannotUnverifyAnotherCreator(int code, String msg) implements TokenMetadataError {

    public static final CannotUnverifyAnotherCreator INSTANCE = new CannotUnverifyAnotherCreator(
        55, "You cannot unilaterally unverify another creator"
    );
  }

  record SpotMismatch(int code, String msg) implements TokenMetadataError {

    public static final SpotMismatch INSTANCE = new SpotMismatch(
        56, ""
    );
  }

  record IncorrectOwner(int code, String msg) implements TokenMetadataError {

    public static final IncorrectOwner INSTANCE = new IncorrectOwner(
        57, "Incorrect account owner"
    );
  }

  record PrintingWouldBreachMaximumSupply(int code, String msg) implements TokenMetadataError {

    public static final PrintingWouldBreachMaximumSupply INSTANCE = new PrintingWouldBreachMaximumSupply(
        58, ""
    );
  }

  record DataIsImmutable(int code, String msg) implements TokenMetadataError {

    public static final DataIsImmutable INSTANCE = new DataIsImmutable(
        59, "Data is immutable"
    );
  }

  record DuplicateCreatorAddress(int code, String msg) implements TokenMetadataError {

    public static final DuplicateCreatorAddress INSTANCE = new DuplicateCreatorAddress(
        60, "No duplicate creator addresses"
    );
  }

  record ReservationSpotsRemainingShouldMatchTotalSpotsAtStart(int code, String msg) implements TokenMetadataError {

    public static final ReservationSpotsRemainingShouldMatchTotalSpotsAtStart INSTANCE = new ReservationSpotsRemainingShouldMatchTotalSpotsAtStart(
        61, ""
    );
  }

  record InvalidTokenProgram(int code, String msg) implements TokenMetadataError {

    public static final InvalidTokenProgram INSTANCE = new InvalidTokenProgram(
        62, "Invalid token program"
    );
  }

  record DataTypeMismatch(int code, String msg) implements TokenMetadataError {

    public static final DataTypeMismatch INSTANCE = new DataTypeMismatch(
        63, "Data type mismatch"
    );
  }

  record BeyondAlottedAddressSize(int code, String msg) implements TokenMetadataError {

    public static final BeyondAlottedAddressSize INSTANCE = new BeyondAlottedAddressSize(
        64, ""
    );
  }

  record ReservationNotComplete(int code, String msg) implements TokenMetadataError {

    public static final ReservationNotComplete INSTANCE = new ReservationNotComplete(
        65, ""
    );
  }

  record TriedToReplaceAnExistingReservation(int code, String msg) implements TokenMetadataError {

    public static final TriedToReplaceAnExistingReservation INSTANCE = new TriedToReplaceAnExistingReservation(
        66, ""
    );
  }

  record InvalidOperation(int code, String msg) implements TokenMetadataError {

    public static final InvalidOperation INSTANCE = new InvalidOperation(
        67, "Invalid operation"
    );
  }

  record InvalidOwner(int code, String msg) implements TokenMetadataError {

    public static final InvalidOwner INSTANCE = new InvalidOwner(
        68, "Invalid Owner"
    );
  }

  record PrintingMintSupplyMustBeZeroForConversion(int code, String msg) implements TokenMetadataError {

    public static final PrintingMintSupplyMustBeZeroForConversion INSTANCE = new PrintingMintSupplyMustBeZeroForConversion(
        69, "Printing mint supply must be zero for conversion"
    );
  }

  record OneTimeAuthMintSupplyMustBeZeroForConversion(int code, String msg) implements TokenMetadataError {

    public static final OneTimeAuthMintSupplyMustBeZeroForConversion INSTANCE = new OneTimeAuthMintSupplyMustBeZeroForConversion(
        70, "One Time Auth mint supply must be zero for conversion"
    );
  }

  record InvalidEditionIndex(int code, String msg) implements TokenMetadataError {

    public static final InvalidEditionIndex INSTANCE = new InvalidEditionIndex(
        71, "You tried to insert one edition too many into an edition mark pda"
    );
  }

  record ReservationArrayShouldBeSizeOne(int code, String msg) implements TokenMetadataError {

    public static final ReservationArrayShouldBeSizeOne INSTANCE = new ReservationArrayShouldBeSizeOne(
        72, ""
    );
  }

  record IsMutableCanOnlyBeFlippedToFalse(int code, String msg) implements TokenMetadataError {

    public static final IsMutableCanOnlyBeFlippedToFalse INSTANCE = new IsMutableCanOnlyBeFlippedToFalse(
        73, "Is Mutable can only be flipped to false"
    );
  }

  record CollectionCannotBeVerifiedInThisInstruction(int code, String msg) implements TokenMetadataError {

    public static final CollectionCannotBeVerifiedInThisInstruction INSTANCE = new CollectionCannotBeVerifiedInThisInstruction(
        74, "Collection cannot be verified in this instruction"
    );
  }

  record Removed(int code, String msg) implements TokenMetadataError {

    public static final Removed INSTANCE = new Removed(
        75, "This instruction was deprecated in a previous release and is now removed"
    );
  }

  record MustBeBurned(int code, String msg) implements TokenMetadataError {

    public static final MustBeBurned INSTANCE = new MustBeBurned(
        76, ""
    );
  }

  record InvalidUseMethod(int code, String msg) implements TokenMetadataError {

    public static final InvalidUseMethod INSTANCE = new InvalidUseMethod(
        77, "This use method is invalid"
    );
  }

  record CannotChangeUseMethodAfterFirstUse(int code, String msg) implements TokenMetadataError {

    public static final CannotChangeUseMethodAfterFirstUse INSTANCE = new CannotChangeUseMethodAfterFirstUse(
        78, "Cannot Change Use Method after the first use"
    );
  }

  record CannotChangeUsesAfterFirstUse(int code, String msg) implements TokenMetadataError {

    public static final CannotChangeUsesAfterFirstUse INSTANCE = new CannotChangeUsesAfterFirstUse(
        79, "Cannot Change Remaining or Available uses after the first use"
    );
  }

  record CollectionNotFound(int code, String msg) implements TokenMetadataError {

    public static final CollectionNotFound INSTANCE = new CollectionNotFound(
        80, "Collection Not Found on Metadata"
    );
  }

  record InvalidCollectionUpdateAuthority(int code, String msg) implements TokenMetadataError {

    public static final InvalidCollectionUpdateAuthority INSTANCE = new InvalidCollectionUpdateAuthority(
        81, "Collection Update Authority is invalid"
    );
  }

  record CollectionMustBeAUniqueMasterEdition(int code, String msg) implements TokenMetadataError {

    public static final CollectionMustBeAUniqueMasterEdition INSTANCE = new CollectionMustBeAUniqueMasterEdition(
        82, "Collection Must Be a Unique Master Edition v2"
    );
  }

  record UseAuthorityRecordAlreadyExists(int code, String msg) implements TokenMetadataError {

    public static final UseAuthorityRecordAlreadyExists INSTANCE = new UseAuthorityRecordAlreadyExists(
        83, "The Use Authority Record Already Exists, to modify it Revoke, then Approve"
    );
  }

  record UseAuthorityRecordAlreadyRevoked(int code, String msg) implements TokenMetadataError {

    public static final UseAuthorityRecordAlreadyRevoked INSTANCE = new UseAuthorityRecordAlreadyRevoked(
        84, "The Use Authority Record is empty or already revoked"
    );
  }

  record Unusable(int code, String msg) implements TokenMetadataError {

    public static final Unusable INSTANCE = new Unusable(
        85, "This token has no uses"
    );
  }

  record NotEnoughUses(int code, String msg) implements TokenMetadataError {

    public static final NotEnoughUses INSTANCE = new NotEnoughUses(
        86, "There are not enough Uses left on this token."
    );
  }

  record CollectionAuthorityRecordAlreadyExists(int code, String msg) implements TokenMetadataError {

    public static final CollectionAuthorityRecordAlreadyExists INSTANCE = new CollectionAuthorityRecordAlreadyExists(
        87, "This Collection Authority Record Already Exists."
    );
  }

  record CollectionAuthorityDoesNotExist(int code, String msg) implements TokenMetadataError {

    public static final CollectionAuthorityDoesNotExist INSTANCE = new CollectionAuthorityDoesNotExist(
        88, "This Collection Authority Record Does Not Exist."
    );
  }

  record InvalidUseAuthorityRecord(int code, String msg) implements TokenMetadataError {

    public static final InvalidUseAuthorityRecord INSTANCE = new InvalidUseAuthorityRecord(
        89, "This Use Authority Record is invalid."
    );
  }

  record InvalidCollectionAuthorityRecord(int code, String msg) implements TokenMetadataError {

    public static final InvalidCollectionAuthorityRecord INSTANCE = new InvalidCollectionAuthorityRecord(
        90, ""
    );
  }

  record InvalidFreezeAuthority(int code, String msg) implements TokenMetadataError {

    public static final InvalidFreezeAuthority INSTANCE = new InvalidFreezeAuthority(
        91, "Metadata does not match the freeze authority on the mint"
    );
  }

  record InvalidDelegate(int code, String msg) implements TokenMetadataError {

    public static final InvalidDelegate INSTANCE = new InvalidDelegate(
        92, "All tokens in this account have not been delegated to this user."
    );
  }

  record CannotAdjustVerifiedCreator(int code, String msg) implements TokenMetadataError {

    public static final CannotAdjustVerifiedCreator INSTANCE = new CannotAdjustVerifiedCreator(
        93, ""
    );
  }

  record CannotRemoveVerifiedCreator(int code, String msg) implements TokenMetadataError {

    public static final CannotRemoveVerifiedCreator INSTANCE = new CannotRemoveVerifiedCreator(
        94, "Verified creators cannot be removed."
    );
  }

  record CannotWipeVerifiedCreators(int code, String msg) implements TokenMetadataError {

    public static final CannotWipeVerifiedCreators INSTANCE = new CannotWipeVerifiedCreators(
        95, ""
    );
  }

  record NotAllowedToChangeSellerFeeBasisPoints(int code, String msg) implements TokenMetadataError {

    public static final NotAllowedToChangeSellerFeeBasisPoints INSTANCE = new NotAllowedToChangeSellerFeeBasisPoints(
        96, ""
    );
  }

  record EditionOverrideCannotBeZero(int code, String msg) implements TokenMetadataError {

    public static final EditionOverrideCannotBeZero INSTANCE = new EditionOverrideCannotBeZero(
        97, "Edition override cannot be zero"
    );
  }

  record InvalidUser(int code, String msg) implements TokenMetadataError {

    public static final InvalidUser INSTANCE = new InvalidUser(
        98, "Invalid User"
    );
  }

  record RevokeCollectionAuthoritySignerIncorrect(int code, String msg) implements TokenMetadataError {

    public static final RevokeCollectionAuthoritySignerIncorrect INSTANCE = new RevokeCollectionAuthoritySignerIncorrect(
        99, "Revoke Collection Authority signer is incorrect"
    );
  }

  record TokenCloseFailed(int code, String msg) implements TokenMetadataError {

    public static final TokenCloseFailed INSTANCE = new TokenCloseFailed(
        100, ""
    );
  }

  record UnsizedCollection(int code, String msg) implements TokenMetadataError {

    public static final UnsizedCollection INSTANCE = new UnsizedCollection(
        101, "Can't use this function on unsized collection"
    );
  }

  record SizedCollection(int code, String msg) implements TokenMetadataError {

    public static final SizedCollection INSTANCE = new SizedCollection(
        102, "Can't use this function on a sized collection"
    );
  }

  record MissingCollectionMetadata(int code, String msg) implements TokenMetadataError {

    public static final MissingCollectionMetadata INSTANCE = new MissingCollectionMetadata(
        103, "Missing collection metadata account"
    );
  }

  record NotAMemberOfCollection(int code, String msg) implements TokenMetadataError {

    public static final NotAMemberOfCollection INSTANCE = new NotAMemberOfCollection(
        104, "This NFT is not a member of the specified collection."
    );
  }

  record NotVerifiedMemberOfCollection(int code, String msg) implements TokenMetadataError {

    public static final NotVerifiedMemberOfCollection INSTANCE = new NotVerifiedMemberOfCollection(
        105, "This NFT is not a verified member of the specified collection."
    );
  }

  record NotACollectionParent(int code, String msg) implements TokenMetadataError {

    public static final NotACollectionParent INSTANCE = new NotACollectionParent(
        106, "This NFT is not a collection parent NFT."
    );
  }

  record CouldNotDetermineTokenStandard(int code, String msg) implements TokenMetadataError {

    public static final CouldNotDetermineTokenStandard INSTANCE = new CouldNotDetermineTokenStandard(
        107, "Could not determine a TokenStandard type."
    );
  }

  record MissingEditionAccount(int code, String msg) implements TokenMetadataError {

    public static final MissingEditionAccount INSTANCE = new MissingEditionAccount(
        108, "This mint account has an edition but none was provided."
    );
  }

  record NotAMasterEdition(int code, String msg) implements TokenMetadataError {

    public static final NotAMasterEdition INSTANCE = new NotAMasterEdition(
        109, "This edition is not a Master Edition"
    );
  }

  record MasterEditionHasPrints(int code, String msg) implements TokenMetadataError {

    public static final MasterEditionHasPrints INSTANCE = new MasterEditionHasPrints(
        110, "This Master Edition has existing prints"
    );
  }

  record BorshDeserializationError(int code, String msg) implements TokenMetadataError {

    public static final BorshDeserializationError INSTANCE = new BorshDeserializationError(
        111, ""
    );
  }

  record CannotUpdateVerifiedCollection(int code, String msg) implements TokenMetadataError {

    public static final CannotUpdateVerifiedCollection INSTANCE = new CannotUpdateVerifiedCollection(
        112, "Cannot update a verified collection in this command"
    );
  }

  record CollectionMasterEditionAccountInvalid(int code, String msg) implements TokenMetadataError {

    public static final CollectionMasterEditionAccountInvalid INSTANCE = new CollectionMasterEditionAccountInvalid(
        113, "Edition account doesnt match collection "
    );
  }

  record AlreadyVerified(int code, String msg) implements TokenMetadataError {

    public static final AlreadyVerified INSTANCE = new AlreadyVerified(
        114, "Item is already verified."
    );
  }

  record AlreadyUnverified(int code, String msg) implements TokenMetadataError {

    public static final AlreadyUnverified INSTANCE = new AlreadyUnverified(
        115, ""
    );
  }

  record NotAPrintEdition(int code, String msg) implements TokenMetadataError {

    public static final NotAPrintEdition INSTANCE = new NotAPrintEdition(
        116, "This edition is not a Print Edition"
    );
  }

  record InvalidMasterEdition(int code, String msg) implements TokenMetadataError {

    public static final InvalidMasterEdition INSTANCE = new InvalidMasterEdition(
        117, "Invalid Master Edition"
    );
  }

  record InvalidPrintEdition(int code, String msg) implements TokenMetadataError {

    public static final InvalidPrintEdition INSTANCE = new InvalidPrintEdition(
        118, "Invalid Print Edition"
    );
  }

  record InvalidEditionMarker(int code, String msg) implements TokenMetadataError {

    public static final InvalidEditionMarker INSTANCE = new InvalidEditionMarker(
        119, "Invalid Edition Marker"
    );
  }

  record ReservationListDeprecated(int code, String msg) implements TokenMetadataError {

    public static final ReservationListDeprecated INSTANCE = new ReservationListDeprecated(
        120, "Reservation List is Deprecated"
    );
  }

  record PrintEditionDoesNotMatchMasterEdition(int code, String msg) implements TokenMetadataError {

    public static final PrintEditionDoesNotMatchMasterEdition INSTANCE = new PrintEditionDoesNotMatchMasterEdition(
        121, "Print Edition does not match Master Edition"
    );
  }

  record EditionNumberGreaterThanMaxSupply(int code, String msg) implements TokenMetadataError {

    public static final EditionNumberGreaterThanMaxSupply INSTANCE = new EditionNumberGreaterThanMaxSupply(
        122, "Edition Number greater than max supply"
    );
  }

  record MustUnverify(int code, String msg) implements TokenMetadataError {

    public static final MustUnverify INSTANCE = new MustUnverify(
        123, "Must unverify before migrating collections."
    );
  }

  record InvalidEscrowBumpSeed(int code, String msg) implements TokenMetadataError {

    public static final InvalidEscrowBumpSeed INSTANCE = new InvalidEscrowBumpSeed(
        124, "Invalid Escrow Account Bump Seed"
    );
  }

  record MustBeEscrowAuthority(int code, String msg) implements TokenMetadataError {

    public static final MustBeEscrowAuthority INSTANCE = new MustBeEscrowAuthority(
        125, "Must Escrow Authority"
    );
  }

  record InvalidSystemProgram(int code, String msg) implements TokenMetadataError {

    public static final InvalidSystemProgram INSTANCE = new InvalidSystemProgram(
        126, "Invalid System Program"
    );
  }

  record MustBeNonFungible(int code, String msg) implements TokenMetadataError {

    public static final MustBeNonFungible INSTANCE = new MustBeNonFungible(
        127, "Must be a Non Fungible Token"
    );
  }

  record InsufficientTokens(int code, String msg) implements TokenMetadataError {

    public static final InsufficientTokens INSTANCE = new InsufficientTokens(
        128, "Insufficient tokens for transfer"
    );
  }

  record BorshSerializationError(int code, String msg) implements TokenMetadataError {

    public static final BorshSerializationError INSTANCE = new BorshSerializationError(
        129, "Borsh Serialization Error"
    );
  }

  record NoFreezeAuthoritySet(int code, String msg) implements TokenMetadataError {

    public static final NoFreezeAuthoritySet INSTANCE = new NoFreezeAuthoritySet(
        130, "Cannot create NFT with no Freeze Authority."
    );
  }

  record InvalidCollectionSizeChange(int code, String msg) implements TokenMetadataError {

    public static final InvalidCollectionSizeChange INSTANCE = new InvalidCollectionSizeChange(
        131, "Invalid collection size change"
    );
  }

  record InvalidBubblegumSigner(int code, String msg) implements TokenMetadataError {

    public static final InvalidBubblegumSigner INSTANCE = new InvalidBubblegumSigner(
        132, "Invalid bubblegum signer"
    );
  }

  record EscrowParentHasDelegate(int code, String msg) implements TokenMetadataError {

    public static final EscrowParentHasDelegate INSTANCE = new EscrowParentHasDelegate(
        133, "Escrow parent cannot have a delegate"
    );
  }

  record MintIsNotSigner(int code, String msg) implements TokenMetadataError {

    public static final MintIsNotSigner INSTANCE = new MintIsNotSigner(
        134, "Mint needs to be signer to initialize the account"
    );
  }

  record InvalidTokenStandard(int code, String msg) implements TokenMetadataError {

    public static final InvalidTokenStandard INSTANCE = new InvalidTokenStandard(
        135, "Invalid token standard"
    );
  }

  record InvalidMintForTokenStandard(int code, String msg) implements TokenMetadataError {

    public static final InvalidMintForTokenStandard INSTANCE = new InvalidMintForTokenStandard(
        136, "Invalid mint account for specified token standard"
    );
  }

  record InvalidAuthorizationRules(int code, String msg) implements TokenMetadataError {

    public static final InvalidAuthorizationRules INSTANCE = new InvalidAuthorizationRules(
        137, "Invalid authorization rules account"
    );
  }

  record MissingAuthorizationRules(int code, String msg) implements TokenMetadataError {

    public static final MissingAuthorizationRules INSTANCE = new MissingAuthorizationRules(
        138, "Missing authorization rules account"
    );
  }

  record MissingProgrammableConfig(int code, String msg) implements TokenMetadataError {

    public static final MissingProgrammableConfig INSTANCE = new MissingProgrammableConfig(
        139, "Missing programmable configuration"
    );
  }

  record InvalidProgrammableConfig(int code, String msg) implements TokenMetadataError {

    public static final InvalidProgrammableConfig INSTANCE = new InvalidProgrammableConfig(
        140, "Invalid programmable configuration"
    );
  }

  record DelegateAlreadyExists(int code, String msg) implements TokenMetadataError {

    public static final DelegateAlreadyExists INSTANCE = new DelegateAlreadyExists(
        141, "Delegate already exists"
    );
  }

  record DelegateNotFound(int code, String msg) implements TokenMetadataError {

    public static final DelegateNotFound INSTANCE = new DelegateNotFound(
        142, "Delegate not found"
    );
  }

  record MissingAccountInBuilder(int code, String msg) implements TokenMetadataError {

    public static final MissingAccountInBuilder INSTANCE = new MissingAccountInBuilder(
        143, "Required account not set in instruction builder"
    );
  }

  record MissingArgumentInBuilder(int code, String msg) implements TokenMetadataError {

    public static final MissingArgumentInBuilder INSTANCE = new MissingArgumentInBuilder(
        144, "Required argument not set in instruction builder"
    );
  }

  record FeatureNotSupported(int code, String msg) implements TokenMetadataError {

    public static final FeatureNotSupported INSTANCE = new FeatureNotSupported(
        145, "Feature not supported currently"
    );
  }

  record InvalidSystemWallet(int code, String msg) implements TokenMetadataError {

    public static final InvalidSystemWallet INSTANCE = new InvalidSystemWallet(
        146, "Invalid system wallet"
    );
  }

  record OnlySaleDelegateCanTransfer(int code, String msg) implements TokenMetadataError {

    public static final OnlySaleDelegateCanTransfer INSTANCE = new OnlySaleDelegateCanTransfer(
        147, "Only the sale delegate can transfer while its set"
    );
  }

  record MissingTokenAccount(int code, String msg) implements TokenMetadataError {

    public static final MissingTokenAccount INSTANCE = new MissingTokenAccount(
        148, "Missing token account"
    );
  }

  record MissingSplTokenProgram(int code, String msg) implements TokenMetadataError {

    public static final MissingSplTokenProgram INSTANCE = new MissingSplTokenProgram(
        149, "Missing SPL token program"
    );
  }

  record MissingAuthorizationRulesProgram(int code, String msg) implements TokenMetadataError {

    public static final MissingAuthorizationRulesProgram INSTANCE = new MissingAuthorizationRulesProgram(
        150, "Missing authorization rules program"
    );
  }

  record InvalidDelegateRoleForTransfer(int code, String msg) implements TokenMetadataError {

    public static final InvalidDelegateRoleForTransfer INSTANCE = new InvalidDelegateRoleForTransfer(
        151, "Invalid delegate role for transfer"
    );
  }

  record InvalidTransferAuthority(int code, String msg) implements TokenMetadataError {

    public static final InvalidTransferAuthority INSTANCE = new InvalidTransferAuthority(
        152, "Invalid transfer authority"
    );
  }

  record InstructionNotSupported(int code, String msg) implements TokenMetadataError {

    public static final InstructionNotSupported INSTANCE = new InstructionNotSupported(
        153, "Instruction not supported for ProgrammableNonFungible assets"
    );
  }

  record KeyMismatch(int code, String msg) implements TokenMetadataError {

    public static final KeyMismatch INSTANCE = new KeyMismatch(
        154, "Public key does not match expected value"
    );
  }

  record LockedToken(int code, String msg) implements TokenMetadataError {

    public static final LockedToken INSTANCE = new LockedToken(
        155, "Token is locked"
    );
  }

  record UnlockedToken(int code, String msg) implements TokenMetadataError {

    public static final UnlockedToken INSTANCE = new UnlockedToken(
        156, "Token is unlocked"
    );
  }

  record MissingDelegateRole(int code, String msg) implements TokenMetadataError {

    public static final MissingDelegateRole INSTANCE = new MissingDelegateRole(
        157, "Missing delegate role"
    );
  }

  record InvalidAuthorityType(int code, String msg) implements TokenMetadataError {

    public static final InvalidAuthorityType INSTANCE = new InvalidAuthorityType(
        158, "Invalid authority type"
    );
  }

  record MissingTokenRecord(int code, String msg) implements TokenMetadataError {

    public static final MissingTokenRecord INSTANCE = new MissingTokenRecord(
        159, "Missing token record account"
    );
  }

  record MintSupplyMustBeZero(int code, String msg) implements TokenMetadataError {

    public static final MintSupplyMustBeZero INSTANCE = new MintSupplyMustBeZero(
        160, "Mint supply must be zero for programmable assets"
    );
  }

  record DataIsEmptyOrZeroed(int code, String msg) implements TokenMetadataError {

    public static final DataIsEmptyOrZeroed INSTANCE = new DataIsEmptyOrZeroed(
        161, "Data is empty or zeroed"
    );
  }

  record MissingTokenOwnerAccount(int code, String msg) implements TokenMetadataError {

    public static final MissingTokenOwnerAccount INSTANCE = new MissingTokenOwnerAccount(
        162, "Missing token owner"
    );
  }

  record InvalidMasterEditionAccountLength(int code, String msg) implements TokenMetadataError {

    public static final InvalidMasterEditionAccountLength INSTANCE = new InvalidMasterEditionAccountLength(
        163, "Master edition account has an invalid length"
    );
  }

  record IncorrectTokenState(int code, String msg) implements TokenMetadataError {

    public static final IncorrectTokenState INSTANCE = new IncorrectTokenState(
        164, "Incorrect token state"
    );
  }

  record InvalidDelegateRole(int code, String msg) implements TokenMetadataError {

    public static final InvalidDelegateRole INSTANCE = new InvalidDelegateRole(
        165, "Invalid delegate role"
    );
  }

  record MissingPrintSupply(int code, String msg) implements TokenMetadataError {

    public static final MissingPrintSupply INSTANCE = new MissingPrintSupply(
        166, "Print supply is required for non-fungibles"
    );
  }

  record MissingMasterEditionAccount(int code, String msg) implements TokenMetadataError {

    public static final MissingMasterEditionAccount INSTANCE = new MissingMasterEditionAccount(
        167, "Missing master edition account"
    );
  }

  record AmountMustBeGreaterThanZero(int code, String msg) implements TokenMetadataError {

    public static final AmountMustBeGreaterThanZero INSTANCE = new AmountMustBeGreaterThanZero(
        168, "Amount must be greater than zero"
    );
  }

  record InvalidDelegateArgs(int code, String msg) implements TokenMetadataError {

    public static final InvalidDelegateArgs INSTANCE = new InvalidDelegateArgs(
        169, "Invalid delegate args"
    );
  }

  record MissingLockedTransferAddress(int code, String msg) implements TokenMetadataError {

    public static final MissingLockedTransferAddress INSTANCE = new MissingLockedTransferAddress(
        170, "Missing address for locked transfer"
    );
  }

  record InvalidLockedTransferAddress(int code, String msg) implements TokenMetadataError {

    public static final InvalidLockedTransferAddress INSTANCE = new InvalidLockedTransferAddress(
        171, "Invalid destination address for locked transfer"
    );
  }

  record DataIncrementLimitExceeded(int code, String msg) implements TokenMetadataError {

    public static final DataIncrementLimitExceeded INSTANCE = new DataIncrementLimitExceeded(
        172, "Exceeded account realloc increase limit"
    );
  }

  record CannotUpdateAssetWithDelegate(int code, String msg) implements TokenMetadataError {

    public static final CannotUpdateAssetWithDelegate INSTANCE = new CannotUpdateAssetWithDelegate(
        173, "Cannot update the rule set of a programmable asset that has a delegate"
    );
  }

  record InvalidAmount(int code, String msg) implements TokenMetadataError {

    public static final InvalidAmount INSTANCE = new InvalidAmount(
        174, "Invalid token amount for this operation or token standard"
    );
  }

  record MissingMasterEditionMintAccount(int code, String msg) implements TokenMetadataError {

    public static final MissingMasterEditionMintAccount INSTANCE = new MissingMasterEditionMintAccount(
        175, "Missing master edition mint account"
    );
  }

  record MissingMasterEditionTokenAccount(int code, String msg) implements TokenMetadataError {

    public static final MissingMasterEditionTokenAccount INSTANCE = new MissingMasterEditionTokenAccount(
        176, "Missing master edition token account"
    );
  }

  record MissingEditionMarkerAccount(int code, String msg) implements TokenMetadataError {

    public static final MissingEditionMarkerAccount INSTANCE = new MissingEditionMarkerAccount(
        177, "Missing edition marker account"
    );
  }

  record CannotBurnWithDelegate(int code, String msg) implements TokenMetadataError {

    public static final CannotBurnWithDelegate INSTANCE = new CannotBurnWithDelegate(
        178, "Cannot burn while persistent delegate is set"
    );
  }

  record MissingEdition(int code, String msg) implements TokenMetadataError {

    public static final MissingEdition INSTANCE = new MissingEdition(
        179, "Missing edition account"
    );
  }

  record InvalidAssociatedTokenAccountProgram(int code, String msg) implements TokenMetadataError {

    public static final InvalidAssociatedTokenAccountProgram INSTANCE = new InvalidAssociatedTokenAccountProgram(
        180, "Invalid Associated Token Account Program"
    );
  }

  record InvalidInstructionsSysvar(int code, String msg) implements TokenMetadataError {

    public static final InvalidInstructionsSysvar INSTANCE = new InvalidInstructionsSysvar(
        181, "Invalid InstructionsSysvar"
    );
  }

  record InvalidParentAccounts(int code, String msg) implements TokenMetadataError {

    public static final InvalidParentAccounts INSTANCE = new InvalidParentAccounts(
        182, "Invalid or Unneeded parent accounts"
    );
  }

  record InvalidUpdateArgs(int code, String msg) implements TokenMetadataError {

    public static final InvalidUpdateArgs INSTANCE = new InvalidUpdateArgs(
        183, "Authority cannot apply all update args"
    );
  }

  record InsufficientTokenBalance(int code, String msg) implements TokenMetadataError {

    public static final InsufficientTokenBalance INSTANCE = new InsufficientTokenBalance(
        184, "Token account does not have enough tokens"
    );
  }

  record MissingCollectionMint(int code, String msg) implements TokenMetadataError {

    public static final MissingCollectionMint INSTANCE = new MissingCollectionMint(
        185, "Missing collection account"
    );
  }

  record MissingCollectionMasterEdition(int code, String msg) implements TokenMetadataError {

    public static final MissingCollectionMasterEdition INSTANCE = new MissingCollectionMasterEdition(
        186, "Missing collection master edition account"
    );
  }

  record InvalidTokenRecord(int code, String msg) implements TokenMetadataError {

    public static final InvalidTokenRecord INSTANCE = new InvalidTokenRecord(
        187, "Invalid token record account"
    );
  }

  record InvalidCloseAuthority(int code, String msg) implements TokenMetadataError {

    public static final InvalidCloseAuthority INSTANCE = new InvalidCloseAuthority(
        188, "The close authority needs to be revoked by the Utility Delegate"
    );
  }

  record InvalidInstruction(int code, String msg) implements TokenMetadataError {

    public static final InvalidInstruction INSTANCE = new InvalidInstruction(
        189, "Invalid or removed instruction"
    );
  }

  record MissingDelegateRecord(int code, String msg) implements TokenMetadataError {

    public static final MissingDelegateRecord INSTANCE = new MissingDelegateRecord(
        190, "Missing delegate record"
    );
  }

  record InvalidFeeAccount(int code, String msg) implements TokenMetadataError {

    public static final InvalidFeeAccount INSTANCE = new InvalidFeeAccount(
        191, ""
    );
  }

  record InvalidMetadataFlags(int code, String msg) implements TokenMetadataError {

    public static final InvalidMetadataFlags INSTANCE = new InvalidMetadataFlags(
        192, ""
    );
  }

  record CannotChangeUpdateAuthorityWithDelegate(int code, String msg) implements TokenMetadataError {

    public static final CannotChangeUpdateAuthorityWithDelegate INSTANCE = new CannotChangeUpdateAuthorityWithDelegate(
        193, "Cannot change the update authority with a delegate"
    );
  }

  record InvalidMintExtensionType(int code, String msg) implements TokenMetadataError {

    public static final InvalidMintExtensionType INSTANCE = new InvalidMintExtensionType(
        194, "Invalid mint extension type"
    );
  }

  record InvalidMintCloseAuthority(int code, String msg) implements TokenMetadataError {

    public static final InvalidMintCloseAuthority INSTANCE = new InvalidMintCloseAuthority(
        195, "Invalid mint close authority"
    );
  }

  record InvalidMetadataPointer(int code, String msg) implements TokenMetadataError {

    public static final InvalidMetadataPointer INSTANCE = new InvalidMetadataPointer(
        196, "Invalid metadata pointer"
    );
  }

  record InvalidTokenExtensionType(int code, String msg) implements TokenMetadataError {

    public static final InvalidTokenExtensionType INSTANCE = new InvalidTokenExtensionType(
        197, "Invalid token extension type"
    );
  }

  record MissingImmutableOwnerExtension(int code, String msg) implements TokenMetadataError {

    public static final MissingImmutableOwnerExtension INSTANCE = new MissingImmutableOwnerExtension(
        198, "Missing immutable owner extension"
    );
  }

  record ExpectedUninitializedAccount(int code, String msg) implements TokenMetadataError {

    public static final ExpectedUninitializedAccount INSTANCE = new ExpectedUninitializedAccount(
        199, "Expected account to be uninitialized"
    );
  }

  record InvalidEditionAccountLength(int code, String msg) implements TokenMetadataError {

    public static final InvalidEditionAccountLength INSTANCE = new InvalidEditionAccountLength(
        200, "Edition account has an invalid length"
    );
  }

  record AccountAlreadyResized(int code, String msg) implements TokenMetadataError {

    public static final AccountAlreadyResized INSTANCE = new AccountAlreadyResized(
        201, "Account has already been resized"
    );
  }

  record ConditionsForClosingNotMet(int code, String msg) implements TokenMetadataError {

    public static final ConditionsForClosingNotMet INSTANCE = new ConditionsForClosingNotMet(
        202, "Conditions for closing not met"
    );
  }
}
