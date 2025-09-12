package software.sava.anchor.programs.metaplex.token.metadata.anchor;

import java.util.List;

import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.ApproveUseAuthorityArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.BurnArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.CreateArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.CreateMasterEditionArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.CreateMetadataAccountArgsV3;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.DelegateArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.LockArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.MintArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.MintNewEditionFromMasterEditionViaTokenArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.PrintArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.RevokeArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.SetCollectionSizeArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.TransferArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.TransferOutOfEscrowArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.UnlockArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.UpdateArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.UpdateMetadataAccountArgsV2;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.UseArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.UtilizeArgs;
import software.sava.anchor.programs.metaplex.token.metadata.anchor.types.VerificationArgs;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.util.Objects.requireNonNullElse;

import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class TokenMetadataProgram {

  public static final Discriminator CREATE_METADATA_ACCOUNT_DISCRIMINATOR = toDiscriminator(75, 73, 45, 178, 212, 194, 127, 113);

  public static Instruction createMetadataAccount(final AccountMeta invokedTokenMetadataProgramMeta,
                                                  // Metadata key (pda of ['metadata', program id, mint id])
                                                  final PublicKey metadataKey,
                                                  // Mint of token asset
                                                  final PublicKey mintKey,
                                                  // Mint authority
                                                  final PublicKey mintAuthorityKey,
                                                  // payer
                                                  final PublicKey payerKey,
                                                  // update authority info
                                                  final PublicKey updateAuthorityKey,
                                                  // System program
                                                  final PublicKey systemProgramKey,
                                                  // Rent info
                                                  final PublicKey rentKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createRead(mintKey),
      createReadOnlySigner(mintAuthorityKey),
      createWritableSigner(payerKey),
      createRead(updateAuthorityKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, CREATE_METADATA_ACCOUNT_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_METADATA_ACCOUNT_DISCRIMINATOR = toDiscriminator(141, 14, 23, 104, 247, 192, 53, 173);

  public static Instruction updateMetadataAccount(final AccountMeta invokedTokenMetadataProgramMeta,
                                                  // Metadata account
                                                  final PublicKey metadataKey,
                                                  // Update authority key
                                                  final PublicKey updateAuthorityKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createReadOnlySigner(updateAuthorityKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, UPDATE_METADATA_ACCOUNT_DISCRIMINATOR);
  }

  public static final Discriminator DEPRECATED_CREATE_MASTER_EDITION_DISCRIMINATOR = toDiscriminator(155, 127, 165, 159, 236, 92, 79, 21);

  public static Instruction deprecatedCreateMasterEdition(final AccountMeta invokedTokenMetadataProgramMeta,
                                                          // Unallocated edition V1 account with address as pda of ['metadata', program id, mint, 'edition']
                                                          final PublicKey editionKey,
                                                          // Metadata mint
                                                          final PublicKey mintKey,
                                                          // Printing mint - A mint you control that can mint tokens that can be exchanged for limited editions of your master edition via the MintNewEditionFromMasterEditionViaToken endpoint
                                                          final PublicKey printingMintKey,
                                                          // One time authorization printing mint - A mint you control that prints tokens that gives the bearer permission to mint any number of tokens from the printing mint one time via an endpoint with the token-metadata program for your metadata. Also burns the token.
                                                          final PublicKey oneTimePrintingAuthorizationMintKey,
                                                          // Current Update authority key
                                                          final PublicKey updateAuthorityKey,
                                                          // Printing mint authority - THIS WILL TRANSFER AUTHORITY AWAY FROM THIS KEY.
                                                          final PublicKey printingMintAuthorityKey,
                                                          // Mint authority on the metadata's mint - THIS WILL TRANSFER AUTHORITY AWAY FROM THIS KEY
                                                          final PublicKey mintAuthorityKey,
                                                          // Metadata account
                                                          final PublicKey metadataKey,
                                                          // payer
                                                          final PublicKey payerKey,
                                                          // Token program
                                                          final PublicKey tokenProgramKey,
                                                          // System program
                                                          final PublicKey systemProgramKey,
                                                          // Rent info
                                                          final PublicKey rentKey,
                                                          // One time authorization printing mint authority - must be provided if using max supply. THIS WILL TRANSFER AUTHORITY AWAY FROM THIS KEY.
                                                          final PublicKey oneTimePrintingAuthorizationMintAuthorityKey) {
    final var keys = List.of(
      createWrite(editionKey),
      createWrite(mintKey),
      createWrite(printingMintKey),
      createWrite(oneTimePrintingAuthorizationMintKey),
      createReadOnlySigner(updateAuthorityKey),
      createReadOnlySigner(printingMintAuthorityKey),
      createReadOnlySigner(mintAuthorityKey),
      createRead(metadataKey),
      createReadOnlySigner(payerKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createReadOnlySigner(oneTimePrintingAuthorizationMintAuthorityKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, DEPRECATED_CREATE_MASTER_EDITION_DISCRIMINATOR);
  }

  public static final Discriminator DEPRECATED_MINT_NEW_EDITION_FROM_MASTER_EDITION_VIA_PRINTING_TOKEN_DISCRIMINATOR = toDiscriminator(154, 36, 174, 111, 190, 80, 155, 228);

  public static Instruction deprecatedMintNewEditionFromMasterEditionViaPrintingToken(final AccountMeta invokedTokenMetadataProgramMeta,
                                                                                      // New Metadata key (pda of ['metadata', program id, mint id])
                                                                                      final PublicKey metadataKey,
                                                                                      // New Edition V1 (pda of ['metadata', program id, mint id, 'edition'])
                                                                                      final PublicKey editionKey,
                                                                                      // Master Record Edition V1 (pda of ['metadata', program id, master metadata mint id, 'edition'])
                                                                                      final PublicKey masterEditionKey,
                                                                                      // Mint of new token - THIS WILL TRANSFER AUTHORITY AWAY FROM THIS KEY
                                                                                      final PublicKey mintKey,
                                                                                      // Mint authority of new mint
                                                                                      final PublicKey mintAuthorityKey,
                                                                                      // Printing Mint of master record edition
                                                                                      final PublicKey printingMintKey,
                                                                                      // Token account containing Printing mint token to be transferred
                                                                                      final PublicKey masterTokenAccountKey,
                                                                                      // Edition pda to mark creation - will be checked for pre-existence. (pda of ['metadata', program id, master mint id, edition_number])
                                                                                      final PublicKey editionMarkerKey,
                                                                                      // Burn authority for this token
                                                                                      final PublicKey burnAuthorityKey,
                                                                                      // payer
                                                                                      final PublicKey payerKey,
                                                                                      // update authority info for new metadata account
                                                                                      final PublicKey masterUpdateAuthorityKey,
                                                                                      // Master record metadata account
                                                                                      final PublicKey masterMetadataKey,
                                                                                      // Token program
                                                                                      final PublicKey tokenProgramKey,
                                                                                      // System program
                                                                                      final PublicKey systemProgramKey,
                                                                                      // Rent info
                                                                                      final PublicKey rentKey,
                                                                                      // Reservation List - If present, and you are on this list, you can get an edition number given by your position on the list.
                                                                                      final PublicKey reservationListKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createWrite(editionKey),
      createWrite(masterEditionKey),
      createWrite(mintKey),
      createReadOnlySigner(mintAuthorityKey),
      createWrite(printingMintKey),
      createWrite(masterTokenAccountKey),
      createWrite(editionMarkerKey),
      createReadOnlySigner(burnAuthorityKey),
      createReadOnlySigner(payerKey),
      createRead(masterUpdateAuthorityKey),
      createRead(masterMetadataKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createWrite(requireNonNullElse(reservationListKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, DEPRECATED_MINT_NEW_EDITION_FROM_MASTER_EDITION_VIA_PRINTING_TOKEN_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_PRIMARY_SALE_HAPPENED_VIA_TOKEN_DISCRIMINATOR = toDiscriminator(172, 129, 173, 210, 222, 129, 243, 98);

  public static Instruction updatePrimarySaleHappenedViaToken(final AccountMeta invokedTokenMetadataProgramMeta,
                                                              // Metadata key (pda of ['metadata', program id, mint id])
                                                              final PublicKey metadataKey,
                                                              // Owner on the token account
                                                              final PublicKey ownerKey,
                                                              // Account containing tokens from the metadata's mint
                                                              final PublicKey tokenKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createReadOnlySigner(ownerKey),
      createRead(tokenKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, UPDATE_PRIMARY_SALE_HAPPENED_VIA_TOKEN_DISCRIMINATOR);
  }

  public static final Discriminator DEPRECATED_SET_RESERVATION_LIST_DISCRIMINATOR = toDiscriminator(68, 28, 66, 19, 59, 203, 190, 142);

  public static Instruction deprecatedSetReservationList(final AccountMeta invokedTokenMetadataProgramMeta,
                                                         // Master Edition V1 key (pda of ['metadata', program id, mint id, 'edition'])
                                                         final PublicKey masterEditionKey,
                                                         // PDA for ReservationList of ['metadata', program id, master edition key, 'reservation', resource-key]
                                                         final PublicKey reservationListKey,
                                                         // The resource you tied the reservation list too
                                                         final PublicKey resourceKey) {
    final var keys = List.of(
      createWrite(masterEditionKey),
      createWrite(reservationListKey),
      createReadOnlySigner(resourceKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, DEPRECATED_SET_RESERVATION_LIST_DISCRIMINATOR);
  }

  public static final Discriminator DEPRECATED_CREATE_RESERVATION_LIST_DISCRIMINATOR = toDiscriminator(171, 227, 161, 158, 1, 176, 105, 72);

  public static Instruction deprecatedCreateReservationList(final AccountMeta invokedTokenMetadataProgramMeta,
                                                            // PDA for ReservationList of ['metadata', program id, master edition key, 'reservation', resource-key]
                                                            final PublicKey reservationListKey,
                                                            // Payer
                                                            final PublicKey payerKey,
                                                            // Update authority
                                                            final PublicKey updateAuthorityKey,
                                                            //  Master Edition V1 key (pda of ['metadata', program id, mint id, 'edition'])
                                                            final PublicKey masterEditionKey,
                                                            // A resource you wish to tie the reservation list to. This is so your later visitors who come to redeem can derive your reservation list PDA with something they can easily get at. You choose what this should be.
                                                            final PublicKey resourceKey,
                                                            // Metadata key (pda of ['metadata', program id, mint id])
                                                            final PublicKey metadataKey,
                                                            // System program
                                                            final PublicKey systemProgramKey,
                                                            // Rent info
                                                            final PublicKey rentKey) {
    final var keys = List.of(
      createWrite(reservationListKey),
      createReadOnlySigner(payerKey),
      createReadOnlySigner(updateAuthorityKey),
      createRead(masterEditionKey),
      createRead(resourceKey),
      createRead(metadataKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, DEPRECATED_CREATE_RESERVATION_LIST_DISCRIMINATOR);
  }

  public static final Discriminator SIGN_METADATA_DISCRIMINATOR = toDiscriminator(178, 245, 253, 205, 236, 250, 233, 209);

  public static Instruction signMetadata(final AccountMeta invokedTokenMetadataProgramMeta,
                                         // Metadata (pda of ['metadata', program id, mint id])
                                         final PublicKey metadataKey,
                                         // Creator
                                         final PublicKey creatorKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createReadOnlySigner(creatorKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, SIGN_METADATA_DISCRIMINATOR);
  }

  public static final Discriminator DEPRECATED_MINT_PRINTING_TOKENS_VIA_TOKEN_DISCRIMINATOR = toDiscriminator(84, 34, 152, 133, 145, 48, 4, 223);

  public static Instruction deprecatedMintPrintingTokensViaToken(final AccountMeta invokedTokenMetadataProgramMeta,
                                                                 // Destination account
                                                                 final PublicKey destinationKey,
                                                                 // Token account containing one time authorization token
                                                                 final PublicKey tokenKey,
                                                                 // One time authorization mint
                                                                 final PublicKey oneTimePrintingAuthorizationMintKey,
                                                                 // Printing mint
                                                                 final PublicKey printingMintKey,
                                                                 // Burn authority
                                                                 final PublicKey burnAuthorityKey,
                                                                 // Metadata key (pda of ['metadata', program id, mint id])
                                                                 final PublicKey metadataKey,
                                                                 // Master Edition V1 key (pda of ['metadata', program id, mint id, 'edition'])
                                                                 final PublicKey masterEditionKey,
                                                                 // Token program
                                                                 final PublicKey tokenProgramKey,
                                                                 // Rent
                                                                 final PublicKey rentKey) {
    final var keys = List.of(
      createWrite(destinationKey),
      createWrite(tokenKey),
      createWrite(oneTimePrintingAuthorizationMintKey),
      createWrite(printingMintKey),
      createReadOnlySigner(burnAuthorityKey),
      createRead(metadataKey),
      createRead(masterEditionKey),
      createRead(tokenProgramKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, DEPRECATED_MINT_PRINTING_TOKENS_VIA_TOKEN_DISCRIMINATOR);
  }

  public static final Discriminator DEPRECATED_MINT_PRINTING_TOKENS_DISCRIMINATOR = toDiscriminator(194, 107, 144, 9, 126, 143, 53, 121);

  public static Instruction deprecatedMintPrintingTokens(final AccountMeta invokedTokenMetadataProgramMeta,
                                                         // Destination account
                                                         final PublicKey destinationKey,
                                                         // Printing mint
                                                         final PublicKey printingMintKey,
                                                         // Update authority
                                                         final PublicKey updateAuthorityKey,
                                                         // Metadata key (pda of ['metadata', program id, mint id])
                                                         final PublicKey metadataKey,
                                                         // Master Edition V1 key (pda of ['metadata', program id, mint id, 'edition'])
                                                         final PublicKey masterEditionKey,
                                                         // Token program
                                                         final PublicKey tokenProgramKey,
                                                         // Rent
                                                         final PublicKey rentKey) {
    final var keys = List.of(
      createWrite(destinationKey),
      createWrite(printingMintKey),
      createReadOnlySigner(updateAuthorityKey),
      createRead(metadataKey),
      createRead(masterEditionKey),
      createRead(tokenProgramKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, DEPRECATED_MINT_PRINTING_TOKENS_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_MASTER_EDITION_DISCRIMINATOR = toDiscriminator(179, 210, 96, 96, 57, 25, 79, 69);

  public static Instruction createMasterEdition(final AccountMeta invokedTokenMetadataProgramMeta,
                                                // Unallocated edition V2 account with address as pda of ['metadata', program id, mint, 'edition']
                                                final PublicKey editionKey,
                                                // Metadata mint
                                                final PublicKey mintKey,
                                                // Update authority
                                                final PublicKey updateAuthorityKey,
                                                // Mint authority on the metadata's mint - THIS WILL TRANSFER AUTHORITY AWAY FROM THIS KEY
                                                final PublicKey mintAuthorityKey,
                                                // payer
                                                final PublicKey payerKey,
                                                // Metadata account
                                                final PublicKey metadataKey,
                                                // Token program
                                                final PublicKey tokenProgramKey,
                                                // System program
                                                final PublicKey systemProgramKey,
                                                // Rent info
                                                final PublicKey rentKey) {
    final var keys = List.of(
      createWrite(editionKey),
      createWrite(mintKey),
      createReadOnlySigner(updateAuthorityKey),
      createReadOnlySigner(mintAuthorityKey),
      createWritableSigner(payerKey),
      createRead(metadataKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, CREATE_MASTER_EDITION_DISCRIMINATOR);
  }

  public static final Discriminator MINT_NEW_EDITION_FROM_MASTER_EDITION_VIA_TOKEN_DISCRIMINATOR = toDiscriminator(252, 218, 191, 168, 126, 69, 125, 118);

  public static Instruction mintNewEditionFromMasterEditionViaToken(final AccountMeta invokedTokenMetadataProgramMeta,
                                                                    // New Metadata key (pda of ['metadata', program id, mint id])
                                                                    final PublicKey newMetadataKey,
                                                                    // New Edition (pda of ['metadata', program id, mint id, 'edition'])
                                                                    final PublicKey newEditionKey,
                                                                    // Master Record Edition V2 (pda of ['metadata', program id, master metadata mint id, 'edition'])
                                                                    final PublicKey masterEditionKey,
                                                                    // Mint of new token - THIS WILL TRANSFER AUTHORITY AWAY FROM THIS KEY
                                                                    final PublicKey newMintKey,
                                                                    // Edition pda to mark creation - will be checked for pre-existence. (pda of ['metadata', program id, master metadata mint id, 'edition', edition_number]) where edition_number is NOT the edition number you pass in args but actually edition_number = floor(edition/EDITION_MARKER_BIT_SIZE).
                                                                    final PublicKey editionMarkPdaKey,
                                                                    // Mint authority of new mint
                                                                    final PublicKey newMintAuthorityKey,
                                                                    // payer
                                                                    final PublicKey payerKey,
                                                                    // owner of token account containing master token (#8)
                                                                    final PublicKey tokenAccountOwnerKey,
                                                                    // token account containing token from master metadata mint
                                                                    final PublicKey tokenAccountKey,
                                                                    // Update authority info for new metadata
                                                                    final PublicKey newMetadataUpdateAuthorityKey,
                                                                    // Master record metadata account
                                                                    final PublicKey metadataKey,
                                                                    // Token program
                                                                    final PublicKey tokenProgramKey,
                                                                    // System program
                                                                    final PublicKey systemProgramKey,
                                                                    // Rent info
                                                                    final PublicKey rentKey,
                                                                    final MintNewEditionFromMasterEditionViaTokenArgs mintNewEditionFromMasterEditionViaTokenArgs) {
    final var keys = List.of(
      createWrite(newMetadataKey),
      createWrite(newEditionKey),
      createWrite(masterEditionKey),
      createWrite(newMintKey),
      createWrite(editionMarkPdaKey),
      createReadOnlySigner(newMintAuthorityKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(tokenAccountOwnerKey),
      createRead(tokenAccountKey),
      createRead(newMetadataUpdateAuthorityKey),
      createRead(metadataKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(requireNonNullElse(rentKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(mintNewEditionFromMasterEditionViaTokenArgs)];
    int i = MINT_NEW_EDITION_FROM_MASTER_EDITION_VIA_TOKEN_DISCRIMINATOR.write(_data, 0);
    Borsh.write(mintNewEditionFromMasterEditionViaTokenArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record MintNewEditionFromMasterEditionViaTokenIxData(Discriminator discriminator, MintNewEditionFromMasterEditionViaTokenArgs mintNewEditionFromMasterEditionViaTokenArgs) implements Borsh {  

    public static MintNewEditionFromMasterEditionViaTokenIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static MintNewEditionFromMasterEditionViaTokenIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintNewEditionFromMasterEditionViaTokenArgs = MintNewEditionFromMasterEditionViaTokenArgs.read(_data, i);
      return new MintNewEditionFromMasterEditionViaTokenIxData(discriminator, mintNewEditionFromMasterEditionViaTokenArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(mintNewEditionFromMasterEditionViaTokenArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CONVERT_MASTER_EDITION_V_1_TO_V_2_DISCRIMINATOR = toDiscriminator(217, 26, 108, 0, 55, 126, 167, 238);

  public static Instruction convertMasterEditionV1ToV2(final AccountMeta invokedTokenMetadataProgramMeta,
                                                       // Master Record Edition V1 (pda of ['metadata', program id, master metadata mint id, 'edition'])
                                                       final PublicKey masterEditionKey,
                                                       // One time authorization mint
                                                       final PublicKey oneTimeAuthKey,
                                                       // Printing mint
                                                       final PublicKey printingMintKey) {
    final var keys = List.of(
      createWrite(masterEditionKey),
      createWrite(oneTimeAuthKey),
      createWrite(printingMintKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, CONVERT_MASTER_EDITION_V_1_TO_V_2_DISCRIMINATOR);
  }

  public static final Discriminator MINT_NEW_EDITION_FROM_MASTER_EDITION_VIA_VAULT_PROXY_DISCRIMINATOR = toDiscriminator(66, 246, 206, 73, 249, 35, 194, 47);

  public static Instruction mintNewEditionFromMasterEditionViaVaultProxy(final AccountMeta invokedTokenMetadataProgramMeta,
                                                                         // New Metadata key (pda of ['metadata', program id, mint id])
                                                                         final PublicKey newMetadataKey,
                                                                         // New Edition (pda of ['metadata', program id, mint id, 'edition'])
                                                                         final PublicKey newEditionKey,
                                                                         // Master Record Edition V2 (pda of ['metadata', program id, master metadata mint id, 'edition']
                                                                         final PublicKey masterEditionKey,
                                                                         // Mint of new token - THIS WILL TRANSFER AUTHORITY AWAY FROM THIS KEY
                                                                         final PublicKey newMintKey,
                                                                         // Edition pda to mark creation - will be checked for pre-existence. (pda of ['metadata', program id, master metadata mint id, 'edition', edition_number]) where edition_number is NOT the edition number you pass in args but actually edition_number = floor(edition/EDITION_MARKER_BIT_SIZE).
                                                                         final PublicKey editionMarkPdaKey,
                                                                         // Mint authority of new mint
                                                                         final PublicKey newMintAuthorityKey,
                                                                         // payer
                                                                         final PublicKey payerKey,
                                                                         // Vault authority
                                                                         final PublicKey vaultAuthorityKey,
                                                                         // Safety deposit token store account
                                                                         final PublicKey safetyDepositStoreKey,
                                                                         // Safety deposit box
                                                                         final PublicKey safetyDepositBoxKey,
                                                                         // Vault
                                                                         final PublicKey vaultKey,
                                                                         // Update authority info for new metadata
                                                                         final PublicKey newMetadataUpdateAuthorityKey,
                                                                         // Master record metadata account
                                                                         final PublicKey metadataKey,
                                                                         // Token program
                                                                         final PublicKey tokenProgramKey,
                                                                         // Token vault program
                                                                         final PublicKey tokenVaultProgramKey,
                                                                         // System program
                                                                         final PublicKey systemProgramKey,
                                                                         // Rent info
                                                                         final PublicKey rentKey,
                                                                         final MintNewEditionFromMasterEditionViaTokenArgs mintNewEditionFromMasterEditionViaTokenArgs) {
    final var keys = List.of(
      createWrite(newMetadataKey),
      createWrite(newEditionKey),
      createWrite(masterEditionKey),
      createWrite(newMintKey),
      createWrite(editionMarkPdaKey),
      createReadOnlySigner(newMintAuthorityKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(vaultAuthorityKey),
      createRead(safetyDepositStoreKey),
      createRead(safetyDepositBoxKey),
      createRead(vaultKey),
      createRead(newMetadataUpdateAuthorityKey),
      createRead(metadataKey),
      createRead(tokenProgramKey),
      createRead(tokenVaultProgramKey),
      createRead(systemProgramKey),
      createRead(requireNonNullElse(rentKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(mintNewEditionFromMasterEditionViaTokenArgs)];
    int i = MINT_NEW_EDITION_FROM_MASTER_EDITION_VIA_VAULT_PROXY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(mintNewEditionFromMasterEditionViaTokenArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record MintNewEditionFromMasterEditionViaVaultProxyIxData(Discriminator discriminator, MintNewEditionFromMasterEditionViaTokenArgs mintNewEditionFromMasterEditionViaTokenArgs) implements Borsh {  

    public static MintNewEditionFromMasterEditionViaVaultProxyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static MintNewEditionFromMasterEditionViaVaultProxyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintNewEditionFromMasterEditionViaTokenArgs = MintNewEditionFromMasterEditionViaTokenArgs.read(_data, i);
      return new MintNewEditionFromMasterEditionViaVaultProxyIxData(discriminator, mintNewEditionFromMasterEditionViaTokenArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(mintNewEditionFromMasterEditionViaTokenArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PUFF_METADATA_DISCRIMINATOR = toDiscriminator(87, 217, 21, 132, 105, 238, 71, 114);

  public static Instruction puffMetadata(final AccountMeta invokedTokenMetadataProgramMeta,
                                         // Metadata account
                                         final PublicKey metadataKey) {
    final var keys = List.of(
      createWrite(metadataKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, PUFF_METADATA_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_METADATA_ACCOUNT_V_2_DISCRIMINATOR = toDiscriminator(202, 132, 152, 229, 216, 217, 137, 212);

  public static Instruction updateMetadataAccountV2(final AccountMeta invokedTokenMetadataProgramMeta,
                                                    // Metadata account
                                                    final PublicKey metadataKey,
                                                    // Update authority key
                                                    final PublicKey updateAuthorityKey,
                                                    final UpdateMetadataAccountArgsV2 updateMetadataAccountArgsV2) {
    final var keys = List.of(
      createWrite(metadataKey),
      createReadOnlySigner(updateAuthorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(updateMetadataAccountArgsV2)];
    int i = UPDATE_METADATA_ACCOUNT_V_2_DISCRIMINATOR.write(_data, 0);
    Borsh.write(updateMetadataAccountArgsV2, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record UpdateMetadataAccountV2IxData(Discriminator discriminator, UpdateMetadataAccountArgsV2 updateMetadataAccountArgsV2) implements Borsh {  

    public static UpdateMetadataAccountV2IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateMetadataAccountV2IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var updateMetadataAccountArgsV2 = UpdateMetadataAccountArgsV2.read(_data, i);
      return new UpdateMetadataAccountV2IxData(discriminator, updateMetadataAccountArgsV2);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(updateMetadataAccountArgsV2, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(updateMetadataAccountArgsV2);
    }
  }

  public static final Discriminator CREATE_METADATA_ACCOUNT_V_2_DISCRIMINATOR = toDiscriminator(24, 73, 41, 237, 44, 142, 194, 254);

  public static Instruction createMetadataAccountV2(final AccountMeta invokedTokenMetadataProgramMeta,
                                                    // Metadata key (pda of ['metadata', program id, mint id])
                                                    final PublicKey metadataKey,
                                                    // Mint of token asset
                                                    final PublicKey mintKey,
                                                    // Mint authority
                                                    final PublicKey mintAuthorityKey,
                                                    // payer
                                                    final PublicKey payerKey,
                                                    // update authority info
                                                    final PublicKey updateAuthorityKey,
                                                    // System program
                                                    final PublicKey systemProgramKey,
                                                    // Rent info
                                                    final PublicKey rentKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createRead(mintKey),
      createReadOnlySigner(mintAuthorityKey),
      createWritableSigner(payerKey),
      createRead(updateAuthorityKey),
      createRead(systemProgramKey),
      createRead(requireNonNullElse(rentKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, CREATE_METADATA_ACCOUNT_V_2_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_MASTER_EDITION_V_3_DISCRIMINATOR = toDiscriminator(147, 149, 17, 159, 74, 134, 114, 237);

  public static Instruction createMasterEditionV3(final AccountMeta invokedTokenMetadataProgramMeta,
                                                  // Unallocated edition V2 account with address as pda of ['metadata', program id, mint, 'edition']
                                                  final PublicKey editionKey,
                                                  // Metadata mint
                                                  final PublicKey mintKey,
                                                  // Update authority
                                                  final PublicKey updateAuthorityKey,
                                                  // Mint authority on the metadata's mint - THIS WILL TRANSFER AUTHORITY AWAY FROM THIS KEY
                                                  final PublicKey mintAuthorityKey,
                                                  // payer
                                                  final PublicKey payerKey,
                                                  // Metadata account
                                                  final PublicKey metadataKey,
                                                  // Token program
                                                  final PublicKey tokenProgramKey,
                                                  // System program
                                                  final PublicKey systemProgramKey,
                                                  // Rent info
                                                  final PublicKey rentKey,
                                                  final CreateMasterEditionArgs createMasterEditionArgs) {
    final var keys = List.of(
      createWrite(editionKey),
      createWrite(mintKey),
      createReadOnlySigner(updateAuthorityKey),
      createReadOnlySigner(mintAuthorityKey),
      createWritableSigner(payerKey),
      createWrite(metadataKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(requireNonNullElse(rentKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(createMasterEditionArgs)];
    int i = CREATE_MASTER_EDITION_V_3_DISCRIMINATOR.write(_data, 0);
    Borsh.write(createMasterEditionArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record CreateMasterEditionV3IxData(Discriminator discriminator, CreateMasterEditionArgs createMasterEditionArgs) implements Borsh {  

    public static CreateMasterEditionV3IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreateMasterEditionV3IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var createMasterEditionArgs = CreateMasterEditionArgs.read(_data, i);
      return new CreateMasterEditionV3IxData(discriminator, createMasterEditionArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(createMasterEditionArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(createMasterEditionArgs);
    }
  }

  public static final Discriminator VERIFY_COLLECTION_DISCRIMINATOR = toDiscriminator(56, 113, 101, 253, 79, 55, 122, 169);

  public static Instruction verifyCollection(final AccountMeta invokedTokenMetadataProgramMeta,
                                             // Metadata account
                                             final PublicKey metadataKey,
                                             // Collection Update authority
                                             final PublicKey collectionAuthorityKey,
                                             // payer
                                             final PublicKey payerKey,
                                             // Mint of the Collection
                                             final PublicKey collectionMintKey,
                                             // Metadata Account of the Collection
                                             final PublicKey collectionKey,
                                             // MasterEdition2 Account of the Collection Token
                                             final PublicKey collectionMasterEditionAccountKey,
                                             // Collection Authority Record PDA
                                             final PublicKey collectionAuthorityRecordKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createWritableSigner(collectionAuthorityKey),
      createWritableSigner(payerKey),
      createRead(collectionMintKey),
      createRead(collectionKey),
      createRead(collectionMasterEditionAccountKey),
      createRead(requireNonNullElse(collectionAuthorityRecordKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, VERIFY_COLLECTION_DISCRIMINATOR);
  }

  public static final Discriminator UTILIZE_DISCRIMINATOR = toDiscriminator(104, 146, 242, 209, 176, 174, 185, 163);

  public static Instruction utilize(final AccountMeta invokedTokenMetadataProgramMeta,
                                    // Metadata account
                                    final PublicKey metadataKey,
                                    // Token Account Of NFT
                                    final PublicKey tokenAccountKey,
                                    // Mint of the Metadata
                                    final PublicKey mintKey,
                                    // A Use Authority / Can be the current Owner of the NFT
                                    final PublicKey useAuthorityKey,
                                    // Owner
                                    final PublicKey ownerKey,
                                    // Token program
                                    final PublicKey tokenProgramKey,
                                    // Associated Token program
                                    final PublicKey ataProgramKey,
                                    // System program
                                    final PublicKey systemProgramKey,
                                    // Rent info
                                    final PublicKey rentKey,
                                    // Use Authority Record PDA If present the program Assumes a delegated use authority
                                    final PublicKey useAuthorityRecordKey,
                                    // Program As Signer (Burner)
                                    final PublicKey burnerKey,
                                    final UtilizeArgs utilizeArgs) {
    final var keys = List.of(
      createWrite(metadataKey),
      createWrite(tokenAccountKey),
      createWrite(mintKey),
      createWritableSigner(useAuthorityKey),
      createRead(ownerKey),
      createRead(tokenProgramKey),
      createRead(ataProgramKey),
      createRead(systemProgramKey),
      createRead(rentKey),
      createWrite(requireNonNullElse(useAuthorityRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(burnerKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(utilizeArgs)];
    int i = UTILIZE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(utilizeArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record UtilizeIxData(Discriminator discriminator, UtilizeArgs utilizeArgs) implements Borsh {  

    public static UtilizeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static UtilizeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var utilizeArgs = UtilizeArgs.read(_data, i);
      return new UtilizeIxData(discriminator, utilizeArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(utilizeArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator APPROVE_USE_AUTHORITY_DISCRIMINATOR = toDiscriminator(14, 4, 77, 134, 86, 23, 37, 236);

  public static Instruction approveUseAuthority(final AccountMeta invokedTokenMetadataProgramMeta,
                                                // Use Authority Record PDA
                                                final PublicKey useAuthorityRecordKey,
                                                // Owner
                                                final PublicKey ownerKey,
                                                // Payer
                                                final PublicKey payerKey,
                                                // A Use Authority
                                                final PublicKey userKey,
                                                // Owned Token Account Of Mint
                                                final PublicKey ownerTokenAccountKey,
                                                // Metadata account
                                                final PublicKey metadataKey,
                                                // Mint of Metadata
                                                final PublicKey mintKey,
                                                // Program As Signer (Burner)
                                                final PublicKey burnerKey,
                                                // Token program
                                                final PublicKey tokenProgramKey,
                                                // System program
                                                final PublicKey systemProgramKey,
                                                // Rent info
                                                final PublicKey rentKey,
                                                final ApproveUseAuthorityArgs approveUseAuthorityArgs) {
    final var keys = List.of(
      createWrite(useAuthorityRecordKey),
      createWritableSigner(ownerKey),
      createWritableSigner(payerKey),
      createRead(userKey),
      createWrite(ownerTokenAccountKey),
      createRead(metadataKey),
      createRead(mintKey),
      createRead(burnerKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(requireNonNullElse(rentKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(approveUseAuthorityArgs)];
    int i = APPROVE_USE_AUTHORITY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(approveUseAuthorityArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record ApproveUseAuthorityIxData(Discriminator discriminator, ApproveUseAuthorityArgs approveUseAuthorityArgs) implements Borsh {  

    public static ApproveUseAuthorityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ApproveUseAuthorityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var approveUseAuthorityArgs = ApproveUseAuthorityArgs.read(_data, i);
      return new ApproveUseAuthorityIxData(discriminator, approveUseAuthorityArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(approveUseAuthorityArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REVOKE_USE_AUTHORITY_DISCRIMINATOR = toDiscriminator(204, 194, 208, 141, 142, 221, 109, 84);

  public static Instruction revokeUseAuthority(final AccountMeta invokedTokenMetadataProgramMeta,
                                               // Use Authority Record PDA
                                               final PublicKey useAuthorityRecordKey,
                                               // Owner
                                               final PublicKey ownerKey,
                                               // A Use Authority
                                               final PublicKey userKey,
                                               // Owned Token Account Of Mint
                                               final PublicKey ownerTokenAccountKey,
                                               // Mint of Metadata
                                               final PublicKey mintKey,
                                               // Metadata account
                                               final PublicKey metadataKey,
                                               // Token program
                                               final PublicKey tokenProgramKey,
                                               // System program
                                               final PublicKey systemProgramKey,
                                               // Rent info
                                               final PublicKey rentKey) {
    final var keys = List.of(
      createWrite(useAuthorityRecordKey),
      createWritableSigner(ownerKey),
      createRead(userKey),
      createWrite(ownerTokenAccountKey),
      createRead(mintKey),
      createRead(metadataKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey),
      createRead(requireNonNullElse(rentKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, REVOKE_USE_AUTHORITY_DISCRIMINATOR);
  }

  public static final Discriminator UNVERIFY_COLLECTION_DISCRIMINATOR = toDiscriminator(250, 251, 42, 106, 41, 137, 186, 168);

  public static Instruction unverifyCollection(final AccountMeta invokedTokenMetadataProgramMeta,
                                               // Metadata account
                                               final PublicKey metadataKey,
                                               // Collection Authority
                                               final PublicKey collectionAuthorityKey,
                                               // Mint of the Collection
                                               final PublicKey collectionMintKey,
                                               // Metadata Account of the Collection
                                               final PublicKey collectionKey,
                                               // MasterEdition2 Account of the Collection Token
                                               final PublicKey collectionMasterEditionAccountKey,
                                               // Collection Authority Record PDA
                                               final PublicKey collectionAuthorityRecordKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createWritableSigner(collectionAuthorityKey),
      createRead(collectionMintKey),
      createRead(collectionKey),
      createRead(collectionMasterEditionAccountKey),
      createRead(requireNonNullElse(collectionAuthorityRecordKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, UNVERIFY_COLLECTION_DISCRIMINATOR);
  }

  public static final Discriminator APPROVE_COLLECTION_AUTHORITY_DISCRIMINATOR = toDiscriminator(254, 136, 208, 39, 65, 66, 27, 111);

  public static Instruction approveCollectionAuthority(final AccountMeta invokedTokenMetadataProgramMeta,
                                                       // Collection Authority Record PDA
                                                       final PublicKey collectionAuthorityRecordKey,
                                                       // A Collection Authority
                                                       final PublicKey newCollectionAuthorityKey,
                                                       // Update Authority of Collection NFT
                                                       final PublicKey updateAuthorityKey,
                                                       // Payer
                                                       final PublicKey payerKey,
                                                       // Collection Metadata account
                                                       final PublicKey metadataKey,
                                                       // Mint of Collection Metadata
                                                       final PublicKey mintKey,
                                                       // System program
                                                       final PublicKey systemProgramKey,
                                                       // Rent info
                                                       final PublicKey rentKey) {
    final var keys = List.of(
      createWrite(collectionAuthorityRecordKey),
      createRead(newCollectionAuthorityKey),
      createWritableSigner(updateAuthorityKey),
      createWritableSigner(payerKey),
      createRead(metadataKey),
      createRead(mintKey),
      createRead(systemProgramKey),
      createRead(requireNonNullElse(rentKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, APPROVE_COLLECTION_AUTHORITY_DISCRIMINATOR);
  }

  public static final Discriminator REVOKE_COLLECTION_AUTHORITY_DISCRIMINATOR = toDiscriminator(31, 139, 135, 198, 29, 48, 160, 154);

  public static Instruction revokeCollectionAuthority(final AccountMeta invokedTokenMetadataProgramMeta,
                                                      // Collection Authority Record PDA
                                                      final PublicKey collectionAuthorityRecordKey,
                                                      // Delegated Collection Authority
                                                      final PublicKey delegateAuthorityKey,
                                                      // Update Authority, or Delegated Authority, of Collection NFT
                                                      final PublicKey revokeAuthorityKey,
                                                      // Metadata account
                                                      final PublicKey metadataKey,
                                                      // Mint of Metadata
                                                      final PublicKey mintKey) {
    final var keys = List.of(
      createWrite(collectionAuthorityRecordKey),
      createWrite(delegateAuthorityKey),
      createWritableSigner(revokeAuthorityKey),
      createRead(metadataKey),
      createRead(mintKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, REVOKE_COLLECTION_AUTHORITY_DISCRIMINATOR);
  }

  public static final Discriminator SET_AND_VERIFY_COLLECTION_DISCRIMINATOR = toDiscriminator(235, 242, 121, 216, 158, 234, 180, 234);

  public static Instruction setAndVerifyCollection(final AccountMeta invokedTokenMetadataProgramMeta,
                                                   // Metadata account
                                                   final PublicKey metadataKey,
                                                   // Collection Update authority
                                                   final PublicKey collectionAuthorityKey,
                                                   // Payer
                                                   final PublicKey payerKey,
                                                   // Update Authority of Collection NFT and NFT
                                                   final PublicKey updateAuthorityKey,
                                                   // Mint of the Collection
                                                   final PublicKey collectionMintKey,
                                                   // Metadata Account of the Collection
                                                   final PublicKey collectionKey,
                                                   // MasterEdition2 Account of the Collection Token
                                                   final PublicKey collectionMasterEditionAccountKey,
                                                   // Collection Authority Record PDA
                                                   final PublicKey collectionAuthorityRecordKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createWritableSigner(collectionAuthorityKey),
      createWritableSigner(payerKey),
      createRead(updateAuthorityKey),
      createRead(collectionMintKey),
      createRead(collectionKey),
      createRead(collectionMasterEditionAccountKey),
      createRead(requireNonNullElse(collectionAuthorityRecordKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, SET_AND_VERIFY_COLLECTION_DISCRIMINATOR);
  }

  public static final Discriminator FREEZE_DELEGATED_ACCOUNT_DISCRIMINATOR = toDiscriminator(14, 16, 189, 180, 116, 19, 96, 127);

  public static Instruction freezeDelegatedAccount(final AccountMeta invokedTokenMetadataProgramMeta,
                                                   // Delegate
                                                   final PublicKey delegateKey,
                                                   // Token account to freeze
                                                   final PublicKey tokenAccountKey,
                                                   // Edition
                                                   final PublicKey editionKey,
                                                   // Token mint
                                                   final PublicKey mintKey,
                                                   // Token Program
                                                   final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWritableSigner(delegateKey),
      createWrite(tokenAccountKey),
      createRead(editionKey),
      createRead(mintKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, FREEZE_DELEGATED_ACCOUNT_DISCRIMINATOR);
  }

  public static final Discriminator THAW_DELEGATED_ACCOUNT_DISCRIMINATOR = toDiscriminator(239, 152, 227, 34, 225, 200, 206, 170);

  public static Instruction thawDelegatedAccount(final AccountMeta invokedTokenMetadataProgramMeta,
                                                 // Delegate
                                                 final PublicKey delegateKey,
                                                 // Token account to thaw
                                                 final PublicKey tokenAccountKey,
                                                 // Edition
                                                 final PublicKey editionKey,
                                                 // Token mint
                                                 final PublicKey mintKey,
                                                 // Token Program
                                                 final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWritableSigner(delegateKey),
      createWrite(tokenAccountKey),
      createRead(editionKey),
      createRead(mintKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, THAW_DELEGATED_ACCOUNT_DISCRIMINATOR);
  }

  public static final Discriminator REMOVE_CREATOR_VERIFICATION_DISCRIMINATOR = toDiscriminator(41, 194, 140, 217, 90, 160, 139, 6);

  public static Instruction removeCreatorVerification(final AccountMeta invokedTokenMetadataProgramMeta,
                                                      // Metadata (pda of ['metadata', program id, mint id])
                                                      final PublicKey metadataKey,
                                                      // Creator
                                                      final PublicKey creatorKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createReadOnlySigner(creatorKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, REMOVE_CREATOR_VERIFICATION_DISCRIMINATOR);
  }

  public static final Discriminator BURN_NFT_DISCRIMINATOR = toDiscriminator(119, 13, 183, 17, 194, 243, 38, 31);

  public static Instruction burnNft(final AccountMeta invokedTokenMetadataProgramMeta,
                                    // Metadata (pda of ['metadata', program id, mint id])
                                    final PublicKey metadataKey,
                                    // NFT owner
                                    final PublicKey ownerKey,
                                    // Mint of the NFT
                                    final PublicKey mintKey,
                                    // Token account to close
                                    final PublicKey tokenAccountKey,
                                    // MasterEdition2 of the NFT
                                    final PublicKey masterEditionAccountKey,
                                    // SPL Token Program
                                    final PublicKey splTokenProgramKey,
                                    // Metadata of the Collection
                                    final PublicKey collectionMetadataKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createWritableSigner(ownerKey),
      createWrite(mintKey),
      createWrite(tokenAccountKey),
      createWrite(masterEditionAccountKey),
      createRead(splTokenProgramKey),
      createWrite(requireNonNullElse(collectionMetadataKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, BURN_NFT_DISCRIMINATOR);
  }

  public static final Discriminator VERIFY_SIZED_COLLECTION_ITEM_DISCRIMINATOR = toDiscriminator(86, 111, 223, 68, 17, 99, 180, 147);

  public static Instruction verifySizedCollectionItem(final AccountMeta invokedTokenMetadataProgramMeta,
                                                      // Metadata account
                                                      final PublicKey metadataKey,
                                                      // Collection Update authority
                                                      final PublicKey collectionAuthorityKey,
                                                      // payer
                                                      final PublicKey payerKey,
                                                      // Mint of the Collection
                                                      final PublicKey collectionMintKey,
                                                      // Metadata Account of the Collection
                                                      final PublicKey collectionKey,
                                                      // MasterEdition2 Account of the Collection Token
                                                      final PublicKey collectionMasterEditionAccountKey,
                                                      // Collection Authority Record PDA
                                                      final PublicKey collectionAuthorityRecordKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createReadOnlySigner(collectionAuthorityKey),
      createWritableSigner(payerKey),
      createRead(collectionMintKey),
      createWrite(collectionKey),
      createRead(collectionMasterEditionAccountKey),
      createRead(requireNonNullElse(collectionAuthorityRecordKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, VERIFY_SIZED_COLLECTION_ITEM_DISCRIMINATOR);
  }

  public static final Discriminator UNVERIFY_SIZED_COLLECTION_ITEM_DISCRIMINATOR = toDiscriminator(161, 187, 194, 156, 158, 154, 144, 221);

  public static Instruction unverifySizedCollectionItem(final AccountMeta invokedTokenMetadataProgramMeta,
                                                        // Metadata account
                                                        final PublicKey metadataKey,
                                                        // Collection Authority
                                                        final PublicKey collectionAuthorityKey,
                                                        // payer
                                                        final PublicKey payerKey,
                                                        // Mint of the Collection
                                                        final PublicKey collectionMintKey,
                                                        // Metadata Account of the Collection
                                                        final PublicKey collectionKey,
                                                        // MasterEdition2 Account of the Collection Token
                                                        final PublicKey collectionMasterEditionAccountKey,
                                                        // Collection Authority Record PDA
                                                        final PublicKey collectionAuthorityRecordKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createReadOnlySigner(collectionAuthorityKey),
      createWritableSigner(payerKey),
      createRead(collectionMintKey),
      createWrite(collectionKey),
      createRead(collectionMasterEditionAccountKey),
      createRead(requireNonNullElse(collectionAuthorityRecordKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, UNVERIFY_SIZED_COLLECTION_ITEM_DISCRIMINATOR);
  }

  public static final Discriminator SET_AND_VERIFY_SIZED_COLLECTION_ITEM_DISCRIMINATOR = toDiscriminator(184, 105, 169, 35, 3, 88, 238, 67);

  public static Instruction setAndVerifySizedCollectionItem(final AccountMeta invokedTokenMetadataProgramMeta,
                                                            // Metadata account
                                                            final PublicKey metadataKey,
                                                            // Collection Update authority
                                                            final PublicKey collectionAuthorityKey,
                                                            // payer
                                                            final PublicKey payerKey,
                                                            // Update Authority of Collection NFT and NFT
                                                            final PublicKey updateAuthorityKey,
                                                            // Mint of the Collection
                                                            final PublicKey collectionMintKey,
                                                            // Metadata Account of the Collection
                                                            final PublicKey collectionKey,
                                                            // MasterEdition2 Account of the Collection Token
                                                            final PublicKey collectionMasterEditionAccountKey,
                                                            // Collection Authority Record PDA
                                                            final PublicKey collectionAuthorityRecordKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createReadOnlySigner(collectionAuthorityKey),
      createWritableSigner(payerKey),
      createRead(updateAuthorityKey),
      createRead(collectionMintKey),
      createWrite(collectionKey),
      createRead(collectionMasterEditionAccountKey),
      createRead(requireNonNullElse(collectionAuthorityRecordKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, SET_AND_VERIFY_SIZED_COLLECTION_ITEM_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_METADATA_ACCOUNT_V_3_DISCRIMINATOR = toDiscriminator(43, 12, 175, 14, 252, 45, 188, 155);

  public static Instruction createMetadataAccountV3(final AccountMeta invokedTokenMetadataProgramMeta,
                                                    // Metadata key (pda of ['metadata', program id, mint id])
                                                    final PublicKey metadataKey,
                                                    // Mint of token asset
                                                    final PublicKey mintKey,
                                                    // Mint authority
                                                    final PublicKey mintAuthorityKey,
                                                    // payer
                                                    final PublicKey payerKey,
                                                    // update authority info
                                                    final PublicKey updateAuthorityKey,
                                                    // System program
                                                    final PublicKey systemProgramKey,
                                                    // Rent info
                                                    final PublicKey rentKey,
                                                    final CreateMetadataAccountArgsV3 createMetadataAccountArgsV3) {
    final var keys = List.of(
      createWrite(metadataKey),
      createRead(mintKey),
      createReadOnlySigner(mintAuthorityKey),
      createWritableSigner(payerKey),
      createRead(updateAuthorityKey),
      createRead(systemProgramKey),
      createRead(requireNonNullElse(rentKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(createMetadataAccountArgsV3)];
    int i = CREATE_METADATA_ACCOUNT_V_3_DISCRIMINATOR.write(_data, 0);
    Borsh.write(createMetadataAccountArgsV3, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record CreateMetadataAccountV3IxData(Discriminator discriminator, CreateMetadataAccountArgsV3 createMetadataAccountArgsV3) implements Borsh {  

    public static CreateMetadataAccountV3IxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreateMetadataAccountV3IxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var createMetadataAccountArgsV3 = CreateMetadataAccountArgsV3.read(_data, i);
      return new CreateMetadataAccountV3IxData(discriminator, createMetadataAccountArgsV3);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(createMetadataAccountArgsV3, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(createMetadataAccountArgsV3);
    }
  }

  public static final Discriminator SET_COLLECTION_SIZE_DISCRIMINATOR = toDiscriminator(157, 254, 166, 144, 43, 223, 199, 39);

  public static Instruction setCollectionSize(final AccountMeta invokedTokenMetadataProgramMeta,
                                              // Collection Metadata account
                                              final PublicKey collectionMetadataKey,
                                              // Collection Update authority
                                              final PublicKey collectionAuthorityKey,
                                              // Mint of the Collection
                                              final PublicKey collectionMintKey,
                                              // Collection Authority Record PDA
                                              final PublicKey collectionAuthorityRecordKey,
                                              final SetCollectionSizeArgs setCollectionSizeArgs) {
    final var keys = List.of(
      createWrite(collectionMetadataKey),
      createWritableSigner(collectionAuthorityKey),
      createRead(collectionMintKey),
      createRead(requireNonNullElse(collectionAuthorityRecordKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(setCollectionSizeArgs)];
    int i = SET_COLLECTION_SIZE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(setCollectionSizeArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record SetCollectionSizeIxData(Discriminator discriminator, SetCollectionSizeArgs setCollectionSizeArgs) implements Borsh {  

    public static SetCollectionSizeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetCollectionSizeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var setCollectionSizeArgs = SetCollectionSizeArgs.read(_data, i);
      return new SetCollectionSizeIxData(discriminator, setCollectionSizeArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(setCollectionSizeArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_TOKEN_STANDARD_DISCRIMINATOR = toDiscriminator(147, 212, 106, 195, 30, 170, 209, 128);

  public static Instruction setTokenStandard(final AccountMeta invokedTokenMetadataProgramMeta,
                                             // Metadata account
                                             final PublicKey metadataKey,
                                             // Metadata update authority
                                             final PublicKey updateAuthorityKey,
                                             // Mint account
                                             final PublicKey mintKey,
                                             // Edition account
                                             final PublicKey editionKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createReadOnlySigner(updateAuthorityKey),
      createRead(mintKey),
      createRead(requireNonNullElse(editionKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, SET_TOKEN_STANDARD_DISCRIMINATOR);
  }

  public static final Discriminator BUBBLEGUM_SET_COLLECTION_SIZE_DISCRIMINATOR = toDiscriminator(230, 215, 231, 226, 156, 188, 56, 6);

  public static Instruction bubblegumSetCollectionSize(final AccountMeta invokedTokenMetadataProgramMeta,
                                                       // Collection Metadata account
                                                       final PublicKey collectionMetadataKey,
                                                       // Collection Update authority
                                                       final PublicKey collectionAuthorityKey,
                                                       // Mint of the Collection
                                                       final PublicKey collectionMintKey,
                                                       // Signing PDA of Bubblegum program
                                                       final PublicKey bubblegumSignerKey,
                                                       // Collection Authority Record PDA
                                                       final PublicKey collectionAuthorityRecordKey,
                                                       final SetCollectionSizeArgs setCollectionSizeArgs) {
    final var keys = List.of(
      createWrite(collectionMetadataKey),
      createReadOnlySigner(collectionAuthorityKey),
      createRead(collectionMintKey),
      createReadOnlySigner(bubblegumSignerKey),
      createRead(requireNonNullElse(collectionAuthorityRecordKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(setCollectionSizeArgs)];
    int i = BUBBLEGUM_SET_COLLECTION_SIZE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(setCollectionSizeArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record BubblegumSetCollectionSizeIxData(Discriminator discriminator, SetCollectionSizeArgs setCollectionSizeArgs) implements Borsh {  

    public static BubblegumSetCollectionSizeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static BubblegumSetCollectionSizeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var setCollectionSizeArgs = SetCollectionSizeArgs.read(_data, i);
      return new BubblegumSetCollectionSizeIxData(discriminator, setCollectionSizeArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(setCollectionSizeArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator BURN_EDITION_NFT_DISCRIMINATOR = toDiscriminator(221, 105, 196, 64, 164, 27, 93, 197);

  public static Instruction burnEditionNft(final AccountMeta invokedTokenMetadataProgramMeta,
                                           // Metadata (pda of ['metadata', program id, mint id])
                                           final PublicKey metadataKey,
                                           // NFT owner
                                           final PublicKey ownerKey,
                                           // Mint of the print edition NFT
                                           final PublicKey printEditionMintKey,
                                           // Mint of the original/master NFT
                                           final PublicKey masterEditionMintKey,
                                           // Token account the print edition NFT is in
                                           final PublicKey printEditionTokenAccountKey,
                                           // Token account the Master Edition NFT is in
                                           final PublicKey masterEditionTokenAccountKey,
                                           // MasterEdition2 of the original NFT
                                           final PublicKey masterEditionAccountKey,
                                           // Print Edition account of the NFT
                                           final PublicKey printEditionAccountKey,
                                           // Edition Marker PDA of the NFT
                                           final PublicKey editionMarkerAccountKey,
                                           // SPL Token Program
                                           final PublicKey splTokenProgramKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createWritableSigner(ownerKey),
      createWrite(printEditionMintKey),
      createRead(masterEditionMintKey),
      createWrite(printEditionTokenAccountKey),
      createRead(masterEditionTokenAccountKey),
      createWrite(masterEditionAccountKey),
      createWrite(printEditionAccountKey),
      createWrite(editionMarkerAccountKey),
      createRead(splTokenProgramKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, BURN_EDITION_NFT_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_ESCROW_ACCOUNT_DISCRIMINATOR = toDiscriminator(146, 147, 225, 47, 51, 64, 112, 1);

  public static Instruction createEscrowAccount(final AccountMeta invokedTokenMetadataProgramMeta,
                                                // Escrow account
                                                final PublicKey escrowKey,
                                                // Metadata account
                                                final PublicKey metadataKey,
                                                // Mint account
                                                final PublicKey mintKey,
                                                // Token account of the token
                                                final PublicKey tokenAccountKey,
                                                // Edition account
                                                final PublicKey editionKey,
                                                // Wallet paying for the transaction and new account
                                                final PublicKey payerKey,
                                                // System program
                                                final PublicKey systemProgramKey,
                                                // Instructions sysvar account
                                                final PublicKey sysvarInstructionsKey,
                                                // Authority/creator of the escrow account
                                                final PublicKey authorityKey) {
    final var keys = List.of(
      createWrite(escrowKey),
      createWrite(metadataKey),
      createRead(mintKey),
      createRead(tokenAccountKey),
      createRead(editionKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey),
      createReadOnlySigner(requireNonNullElse(authorityKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, CREATE_ESCROW_ACCOUNT_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_ESCROW_ACCOUNT_DISCRIMINATOR = toDiscriminator(209, 42, 208, 179, 140, 78, 18, 43);

  public static Instruction closeEscrowAccount(final AccountMeta invokedTokenMetadataProgramMeta,
                                               // Escrow account
                                               final PublicKey escrowKey,
                                               // Metadata account
                                               final PublicKey metadataKey,
                                               // Mint account
                                               final PublicKey mintKey,
                                               // Token account
                                               final PublicKey tokenAccountKey,
                                               // Edition account
                                               final PublicKey editionKey,
                                               // Wallet paying for the transaction and new account
                                               final PublicKey payerKey,
                                               // System program
                                               final PublicKey systemProgramKey,
                                               // Instructions sysvar account
                                               final PublicKey sysvarInstructionsKey) {
    final var keys = List.of(
      createWrite(escrowKey),
      createWrite(metadataKey),
      createRead(mintKey),
      createRead(tokenAccountKey),
      createRead(editionKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, CLOSE_ESCROW_ACCOUNT_DISCRIMINATOR);
  }

  public static final Discriminator TRANSFER_OUT_OF_ESCROW_DISCRIMINATOR = toDiscriminator(55, 186, 186, 216, 115, 158, 58, 153);

  public static Instruction transferOutOfEscrow(final AccountMeta invokedTokenMetadataProgramMeta,
                                                // Escrow account
                                                final PublicKey escrowKey,
                                                // Metadata account
                                                final PublicKey metadataKey,
                                                // Wallet paying for the transaction and new account
                                                final PublicKey payerKey,
                                                // Mint account for the new attribute
                                                final PublicKey attributeMintKey,
                                                // Token account source for the new attribute
                                                final PublicKey attributeSrcKey,
                                                // Token account, owned by TM, destination for the new attribute
                                                final PublicKey attributeDstKey,
                                                // Mint account that the escrow is attached
                                                final PublicKey escrowMintKey,
                                                // Token account that holds the token the escrow is attached to
                                                final PublicKey escrowAccountKey,
                                                // System program
                                                final PublicKey systemProgramKey,
                                                // Associated Token program
                                                final PublicKey ataProgramKey,
                                                // Token program
                                                final PublicKey tokenProgramKey,
                                                // Instructions sysvar account
                                                final PublicKey sysvarInstructionsKey,
                                                // Authority/creator of the escrow account
                                                final PublicKey authorityKey,
                                                final TransferOutOfEscrowArgs transferOutOfEscrowArgs) {
    final var keys = List.of(
      createRead(escrowKey),
      createWrite(metadataKey),
      createWritableSigner(payerKey),
      createRead(attributeMintKey),
      createWrite(attributeSrcKey),
      createWrite(attributeDstKey),
      createRead(escrowMintKey),
      createRead(escrowAccountKey),
      createRead(systemProgramKey),
      createRead(ataProgramKey),
      createRead(tokenProgramKey),
      createRead(sysvarInstructionsKey),
      createReadOnlySigner(requireNonNullElse(authorityKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(transferOutOfEscrowArgs)];
    int i = TRANSFER_OUT_OF_ESCROW_DISCRIMINATOR.write(_data, 0);
    Borsh.write(transferOutOfEscrowArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record TransferOutOfEscrowIxData(Discriminator discriminator, TransferOutOfEscrowArgs transferOutOfEscrowArgs) implements Borsh {  

    public static TransferOutOfEscrowIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static TransferOutOfEscrowIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var transferOutOfEscrowArgs = TransferOutOfEscrowArgs.read(_data, i);
      return new TransferOutOfEscrowIxData(discriminator, transferOutOfEscrowArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(transferOutOfEscrowArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator BURN_DISCRIMINATOR = toDiscriminator(116, 110, 29, 56, 107, 219, 42, 93);

  public static Instruction burn(final AccountMeta invokedTokenMetadataProgramMeta,
                                 // Asset owner or Utility delegate
                                 final PublicKey authorityKey,
                                 // Metadata of the Collection
                                 final PublicKey collectionMetadataKey,
                                 // Metadata (pda of ['metadata', program id, mint id])
                                 final PublicKey metadataKey,
                                 // Edition of the asset
                                 final PublicKey editionKey,
                                 // Mint of token asset
                                 final PublicKey mintKey,
                                 // Token account to close
                                 final PublicKey tokenKey,
                                 // Master edition account
                                 final PublicKey masterEditionKey,
                                 // Master edition mint of the asset
                                 final PublicKey masterEditionMintKey,
                                 // Master edition token account
                                 final PublicKey masterEditionTokenKey,
                                 // Edition marker account
                                 final PublicKey editionMarkerKey,
                                 // Token record account
                                 final PublicKey tokenRecordKey,
                                 // System program
                                 final PublicKey systemProgramKey,
                                 // Instructions sysvar account
                                 final PublicKey sysvarInstructionsKey,
                                 // SPL Token Program
                                 final PublicKey splTokenProgramKey,
                                 final BurnArgs burnArgs) {
    final var keys = List.of(
      createWritableSigner(authorityKey),
      createWrite(requireNonNullElse(collectionMetadataKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(metadataKey),
      createWrite(requireNonNullElse(editionKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(mintKey),
      createWrite(tokenKey),
      createWrite(requireNonNullElse(masterEditionKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(masterEditionMintKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(masterEditionTokenKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(requireNonNullElse(editionMarkerKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(requireNonNullElse(tokenRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey),
      createRead(splTokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(burnArgs)];
    int i = BURN_DISCRIMINATOR.write(_data, 0);
    Borsh.write(burnArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record BurnIxData(Discriminator discriminator, BurnArgs burnArgs) implements Borsh {  

    public static BurnIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static BurnIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var burnArgs = BurnArgs.read(_data, i);
      return new BurnIxData(discriminator, burnArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(burnArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(burnArgs);
    }
  }

  public static final Discriminator CREATE_DISCRIMINATOR = toDiscriminator(24, 30, 200, 40, 5, 28, 7, 119);

  public static Instruction create(final AccountMeta invokedTokenMetadataProgramMeta,
                                   // Unallocated metadata account with address as pda of ['metadata', program id, mint id]
                                   final PublicKey metadataKey,
                                   // Unallocated edition account with address as pda of ['metadata', program id, mint, 'edition']
                                   final PublicKey masterEditionKey,
                                   // Mint of token asset
                                   final PublicKey mintKey,
                                   // Mint authority
                                   final PublicKey authorityKey,
                                   // Payer
                                   final PublicKey payerKey,
                                   // Update authority for the metadata account
                                   final PublicKey updateAuthorityKey,
                                   // System program
                                   final PublicKey systemProgramKey,
                                   // Instructions sysvar account
                                   final PublicKey sysvarInstructionsKey,
                                   // SPL Token program
                                   final PublicKey splTokenProgramKey,
                                   final CreateArgs createArgs) {
    final var keys = List.of(
      createWrite(metadataKey),
      createWrite(requireNonNullElse(masterEditionKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(mintKey),
      createReadOnlySigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(updateAuthorityKey),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey),
      createRead(requireNonNullElse(splTokenProgramKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(createArgs)];
    int i = CREATE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(createArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record CreateIxData(Discriminator discriminator, CreateArgs createArgs) implements Borsh {  

    public static CreateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var createArgs = CreateArgs.read(_data, i);
      return new CreateIxData(discriminator, createArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(createArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(createArgs);
    }
  }

  public static final Discriminator MINT_DISCRIMINATOR = toDiscriminator(51, 57, 225, 47, 182, 146, 137, 166);

  public static Instruction mint(final AccountMeta invokedTokenMetadataProgramMeta,
                                 // Token or Associated Token account
                                 final PublicKey tokenKey,
                                 // Owner of the token account
                                 final PublicKey tokenOwnerKey,
                                 // Metadata account (pda of ['metadata', program id, mint id])
                                 final PublicKey metadataKey,
                                 // Master Edition account
                                 final PublicKey masterEditionKey,
                                 // Token record account
                                 final PublicKey tokenRecordKey,
                                 // Mint of token asset
                                 final PublicKey mintKey,
                                 // (Mint or Update) authority
                                 final PublicKey authorityKey,
                                 // Metadata delegate record
                                 final PublicKey delegateRecordKey,
                                 // Payer
                                 final PublicKey payerKey,
                                 // System program
                                 final PublicKey systemProgramKey,
                                 // Instructions sysvar account
                                 final PublicKey sysvarInstructionsKey,
                                 // SPL Token program
                                 final PublicKey splTokenProgramKey,
                                 // SPL Associated Token Account program
                                 final PublicKey splAtaProgramKey,
                                 // Token Authorization Rules program
                                 final PublicKey authorizationRulesProgramKey,
                                 // Token Authorization Rules account
                                 final PublicKey authorizationRulesKey,
                                 final MintArgs mintArgs) {
    final var keys = List.of(
      createWrite(tokenKey),
      createRead(requireNonNullElse(tokenOwnerKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(metadataKey),
      createWrite(requireNonNullElse(masterEditionKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(requireNonNullElse(tokenRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(mintKey),
      createReadOnlySigner(authorityKey),
      createRead(requireNonNullElse(delegateRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey),
      createRead(splTokenProgramKey),
      createRead(splAtaProgramKey),
      createRead(requireNonNullElse(authorizationRulesProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(mintArgs)];
    int i = MINT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(mintArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record MintIxData(Discriminator discriminator, MintArgs mintArgs) implements Borsh {  

    public static MintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static MintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var mintArgs = MintArgs.read(_data, i);
      return new MintIxData(discriminator, mintArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(mintArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(mintArgs);
    }
  }

  public static final Discriminator DELEGATE_DISCRIMINATOR = toDiscriminator(90, 147, 75, 178, 85, 88, 4, 137);

  public static Instruction delegate(final AccountMeta invokedTokenMetadataProgramMeta,
                                     // Delegate record account
                                     final PublicKey delegateRecordKey,
                                     // Owner of the delegated account
                                     final PublicKey delegateKey,
                                     // Metadata account
                                     final PublicKey metadataKey,
                                     // Master Edition account
                                     final PublicKey masterEditionKey,
                                     // Token record account
                                     final PublicKey tokenRecordKey,
                                     // Mint of metadata
                                     final PublicKey mintKey,
                                     // Token account of mint
                                     final PublicKey tokenKey,
                                     // Update authority or token owner
                                     final PublicKey authorityKey,
                                     // Payer
                                     final PublicKey payerKey,
                                     // System Program
                                     final PublicKey systemProgramKey,
                                     // Instructions sysvar account
                                     final PublicKey sysvarInstructionsKey,
                                     // SPL Token Program
                                     final PublicKey splTokenProgramKey,
                                     // Token Authorization Rules Program
                                     final PublicKey authorizationRulesProgramKey,
                                     // Token Authorization Rules account
                                     final PublicKey authorizationRulesKey,
                                     final DelegateArgs delegateArgs) {
    final var keys = List.of(
      createWrite(requireNonNullElse(delegateRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(delegateKey),
      createWrite(metadataKey),
      createRead(requireNonNullElse(masterEditionKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(requireNonNullElse(tokenRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(mintKey),
      createWrite(requireNonNullElse(tokenKey, invokedTokenMetadataProgramMeta.publicKey())),
      createReadOnlySigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey),
      createRead(requireNonNullElse(splTokenProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(delegateArgs)];
    int i = DELEGATE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(delegateArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record DelegateIxData(Discriminator discriminator, DelegateArgs delegateArgs) implements Borsh {  

    public static DelegateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static DelegateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var delegateArgs = DelegateArgs.read(_data, i);
      return new DelegateIxData(discriminator, delegateArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(delegateArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(delegateArgs);
    }
  }

  public static final Discriminator REVOKE_DISCRIMINATOR = toDiscriminator(170, 23, 31, 34, 133, 173, 93, 242);

  public static Instruction revoke(final AccountMeta invokedTokenMetadataProgramMeta,
                                   // Delegate record account
                                   final PublicKey delegateRecordKey,
                                   // Owner of the delegated account
                                   final PublicKey delegateKey,
                                   // Metadata account
                                   final PublicKey metadataKey,
                                   // Master Edition account
                                   final PublicKey masterEditionKey,
                                   // Token record account
                                   final PublicKey tokenRecordKey,
                                   // Mint of metadata
                                   final PublicKey mintKey,
                                   // Token account of mint
                                   final PublicKey tokenKey,
                                   // Update authority or token owner
                                   final PublicKey authorityKey,
                                   // Payer
                                   final PublicKey payerKey,
                                   // System Program
                                   final PublicKey systemProgramKey,
                                   // Instructions sysvar account
                                   final PublicKey sysvarInstructionsKey,
                                   // SPL Token Program
                                   final PublicKey splTokenProgramKey,
                                   // Token Authorization Rules Program
                                   final PublicKey authorizationRulesProgramKey,
                                   // Token Authorization Rules account
                                   final PublicKey authorizationRulesKey,
                                   final RevokeArgs revokeArgs) {
    final var keys = List.of(
      createWrite(requireNonNullElse(delegateRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(delegateKey),
      createWrite(metadataKey),
      createRead(requireNonNullElse(masterEditionKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(requireNonNullElse(tokenRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(mintKey),
      createWrite(requireNonNullElse(tokenKey, invokedTokenMetadataProgramMeta.publicKey())),
      createReadOnlySigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey),
      createRead(requireNonNullElse(splTokenProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(revokeArgs)];
    int i = REVOKE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(revokeArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record RevokeIxData(Discriminator discriminator, RevokeArgs revokeArgs) implements Borsh {  

    public static RevokeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static RevokeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var revokeArgs = RevokeArgs.read(_data, i);
      return new RevokeIxData(discriminator, revokeArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(revokeArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LOCK_DISCRIMINATOR = toDiscriminator(21, 19, 208, 43, 237, 62, 255, 87);

  public static Instruction lock(final AccountMeta invokedTokenMetadataProgramMeta,
                                 // Delegate or freeze authority
                                 final PublicKey authorityKey,
                                 // Token owner account
                                 final PublicKey tokenOwnerKey,
                                 // Token account
                                 final PublicKey tokenKey,
                                 // Mint account
                                 final PublicKey mintKey,
                                 // Metadata account
                                 final PublicKey metadataKey,
                                 // Edition account
                                 final PublicKey editionKey,
                                 // Token record account
                                 final PublicKey tokenRecordKey,
                                 // Payer
                                 final PublicKey payerKey,
                                 // System program
                                 final PublicKey systemProgramKey,
                                 // System program
                                 final PublicKey sysvarInstructionsKey,
                                 // SPL Token Program
                                 final PublicKey splTokenProgramKey,
                                 // Token Authorization Rules Program
                                 final PublicKey authorizationRulesProgramKey,
                                 // Token Authorization Rules account
                                 final PublicKey authorizationRulesKey,
                                 final LockArgs lockArgs) {
    final var keys = List.of(
      createReadOnlySigner(authorityKey),
      createRead(requireNonNullElse(tokenOwnerKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(tokenKey),
      createRead(mintKey),
      createWrite(metadataKey),
      createRead(requireNonNullElse(editionKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(requireNonNullElse(tokenRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey),
      createRead(requireNonNullElse(splTokenProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(lockArgs)];
    int i = LOCK_DISCRIMINATOR.write(_data, 0);
    Borsh.write(lockArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record LockIxData(Discriminator discriminator, LockArgs lockArgs) implements Borsh {  

    public static LockIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static LockIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lockArgs = LockArgs.read(_data, i);
      return new LockIxData(discriminator, lockArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(lockArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(lockArgs);
    }
  }

  public static final Discriminator UNLOCK_DISCRIMINATOR = toDiscriminator(101, 155, 40, 21, 158, 189, 56, 203);

  public static Instruction unlock(final AccountMeta invokedTokenMetadataProgramMeta,
                                   // Delegate or freeze authority
                                   final PublicKey authorityKey,
                                   // Token owner account
                                   final PublicKey tokenOwnerKey,
                                   // Token account
                                   final PublicKey tokenKey,
                                   // Mint account
                                   final PublicKey mintKey,
                                   // Metadata account
                                   final PublicKey metadataKey,
                                   // Edition account
                                   final PublicKey editionKey,
                                   // Token record account
                                   final PublicKey tokenRecordKey,
                                   // Payer
                                   final PublicKey payerKey,
                                   // System program
                                   final PublicKey systemProgramKey,
                                   // System program
                                   final PublicKey sysvarInstructionsKey,
                                   // SPL Token Program
                                   final PublicKey splTokenProgramKey,
                                   // Token Authorization Rules Program
                                   final PublicKey authorizationRulesProgramKey,
                                   // Token Authorization Rules account
                                   final PublicKey authorizationRulesKey,
                                   final UnlockArgs unlockArgs) {
    final var keys = List.of(
      createReadOnlySigner(authorityKey),
      createRead(requireNonNullElse(tokenOwnerKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(tokenKey),
      createRead(mintKey),
      createWrite(metadataKey),
      createRead(requireNonNullElse(editionKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(requireNonNullElse(tokenRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey),
      createRead(requireNonNullElse(splTokenProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(unlockArgs)];
    int i = UNLOCK_DISCRIMINATOR.write(_data, 0);
    Borsh.write(unlockArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record UnlockIxData(Discriminator discriminator, UnlockArgs unlockArgs) implements Borsh {  

    public static UnlockIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UnlockIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var unlockArgs = UnlockArgs.read(_data, i);
      return new UnlockIxData(discriminator, unlockArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(unlockArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(unlockArgs);
    }
  }

  public static final Discriminator MIGRATE_DISCRIMINATOR = toDiscriminator(155, 234, 231, 146, 236, 158, 162, 30);

  public static Instruction migrate(final AccountMeta invokedTokenMetadataProgramMeta,
                                    // Metadata account
                                    final PublicKey metadataKey,
                                    // Edition account
                                    final PublicKey editionKey,
                                    // Token account
                                    final PublicKey tokenKey,
                                    // Token account owner
                                    final PublicKey tokenOwnerKey,
                                    // Mint account
                                    final PublicKey mintKey,
                                    // Payer
                                    final PublicKey payerKey,
                                    // Update authority
                                    final PublicKey authorityKey,
                                    // Collection metadata account
                                    final PublicKey collectionMetadataKey,
                                    // Delegate record account
                                    final PublicKey delegateRecordKey,
                                    // Token record account
                                    final PublicKey tokenRecordKey,
                                    // System program
                                    final PublicKey systemProgramKey,
                                    // Instruction sysvar account
                                    final PublicKey sysvarInstructionsKey,
                                    // SPL Token Program
                                    final PublicKey splTokenProgramKey,
                                    // Token Authorization Rules Program
                                    final PublicKey authorizationRulesProgramKey,
                                    // Token Authorization Rules account
                                    final PublicKey authorizationRulesKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createWrite(editionKey),
      createWrite(tokenKey),
      createRead(tokenOwnerKey),
      createRead(mintKey),
      createWritableSigner(payerKey),
      createReadOnlySigner(authorityKey),
      createRead(collectionMetadataKey),
      createRead(delegateRecordKey),
      createWrite(tokenRecordKey),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey),
      createRead(splTokenProgramKey),
      createRead(requireNonNullElse(authorizationRulesProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, MIGRATE_DISCRIMINATOR);
  }

  public static final Discriminator TRANSFER_DISCRIMINATOR = toDiscriminator(163, 52, 200, 231, 140, 3, 69, 186);

  public static Instruction transfer(final AccountMeta invokedTokenMetadataProgramMeta,
                                     // Token account
                                     final PublicKey tokenKey,
                                     // Token account owner
                                     final PublicKey tokenOwnerKey,
                                     // Destination token account
                                     final PublicKey destinationKey,
                                     // Destination token account owner
                                     final PublicKey destinationOwnerKey,
                                     // Mint of token asset
                                     final PublicKey mintKey,
                                     // Metadata (pda of ['metadata', program id, mint id])
                                     final PublicKey metadataKey,
                                     // Edition of token asset
                                     final PublicKey editionKey,
                                     // Owner token record account
                                     final PublicKey ownerTokenRecordKey,
                                     // Destination token record account
                                     final PublicKey destinationTokenRecordKey,
                                     // Transfer authority (token owner or delegate)
                                     final PublicKey authorityKey,
                                     // Payer
                                     final PublicKey payerKey,
                                     // System Program
                                     final PublicKey systemProgramKey,
                                     // Instructions sysvar account
                                     final PublicKey sysvarInstructionsKey,
                                     // SPL Token Program
                                     final PublicKey splTokenProgramKey,
                                     // SPL Associated Token Account program
                                     final PublicKey splAtaProgramKey,
                                     // Token Authorization Rules Program
                                     final PublicKey authorizationRulesProgramKey,
                                     // Token Authorization Rules account
                                     final PublicKey authorizationRulesKey,
                                     final TransferArgs transferArgs) {
    final var keys = List.of(
      createWrite(tokenKey),
      createRead(tokenOwnerKey),
      createWrite(destinationKey),
      createRead(destinationOwnerKey),
      createRead(mintKey),
      createWrite(metadataKey),
      createRead(requireNonNullElse(editionKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(requireNonNullElse(ownerTokenRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(requireNonNullElse(destinationTokenRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createReadOnlySigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey),
      createRead(splTokenProgramKey),
      createRead(splAtaProgramKey),
      createRead(requireNonNullElse(authorizationRulesProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(transferArgs)];
    int i = TRANSFER_DISCRIMINATOR.write(_data, 0);
    Borsh.write(transferArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record TransferIxData(Discriminator discriminator, TransferArgs transferArgs) implements Borsh {  

    public static TransferIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static TransferIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var transferArgs = TransferArgs.read(_data, i);
      return new TransferIxData(discriminator, transferArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(transferArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(transferArgs);
    }
  }

  public static final Discriminator UPDATE_DISCRIMINATOR = toDiscriminator(219, 200, 88, 176, 158, 63, 253, 127);

  public static Instruction update(final AccountMeta invokedTokenMetadataProgramMeta,
                                   // Update authority or delegate
                                   final PublicKey authorityKey,
                                   // Delegate record PDA
                                   final PublicKey delegateRecordKey,
                                   // Token account
                                   final PublicKey tokenKey,
                                   // Mint account
                                   final PublicKey mintKey,
                                   // Metadata account
                                   final PublicKey metadataKey,
                                   // Edition account
                                   final PublicKey editionKey,
                                   // Payer
                                   final PublicKey payerKey,
                                   // System program
                                   final PublicKey systemProgramKey,
                                   // Instructions sysvar account
                                   final PublicKey sysvarInstructionsKey,
                                   // Token Authorization Rules Program
                                   final PublicKey authorizationRulesProgramKey,
                                   // Token Authorization Rules account
                                   final PublicKey authorizationRulesKey,
                                   final UpdateArgs updateArgs) {
    final var keys = List.of(
      createReadOnlySigner(authorityKey),
      createRead(requireNonNullElse(delegateRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(tokenKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(mintKey),
      createWrite(metadataKey),
      createRead(requireNonNullElse(editionKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWritableSigner(payerKey),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey),
      createRead(requireNonNullElse(authorizationRulesProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(updateArgs)];
    int i = UPDATE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(updateArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record UpdateIxData(Discriminator discriminator, UpdateArgs updateArgs) implements Borsh {  

    public static UpdateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var updateArgs = UpdateArgs.read(_data, i);
      return new UpdateIxData(discriminator, updateArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(updateArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(updateArgs);
    }
  }

  public static final Discriminator USE_DISCRIMINATOR = toDiscriminator(86, 205, 116, 166, 12, 177, 252, 83);

  public static Instruction use(final AccountMeta invokedTokenMetadataProgramMeta,
                                // Token owner or delegate
                                final PublicKey authorityKey,
                                // Delegate record PDA
                                final PublicKey delegateRecordKey,
                                // Token account
                                final PublicKey tokenKey,
                                // Mint account
                                final PublicKey mintKey,
                                // Metadata account
                                final PublicKey metadataKey,
                                // Edition account
                                final PublicKey editionKey,
                                // Payer
                                final PublicKey payerKey,
                                // System program
                                final PublicKey systemProgramKey,
                                // System program
                                final PublicKey sysvarInstructionsKey,
                                // SPL Token Program
                                final PublicKey splTokenProgramKey,
                                // Token Authorization Rules Program
                                final PublicKey authorizationRulesProgramKey,
                                // Token Authorization Rules account
                                final PublicKey authorizationRulesKey,
                                final UseArgs useArgs) {
    final var keys = List.of(
      createReadOnlySigner(authorityKey),
      createWrite(requireNonNullElse(delegateRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(requireNonNullElse(tokenKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(mintKey),
      createWrite(metadataKey),
      createWrite(requireNonNullElse(editionKey, invokedTokenMetadataProgramMeta.publicKey())),
      createReadOnlySigner(payerKey),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey),
      createRead(requireNonNullElse(splTokenProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesProgramKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(authorizationRulesKey, invokedTokenMetadataProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(useArgs)];
    int i = USE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(useArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record UseIxData(Discriminator discriminator, UseArgs useArgs) implements Borsh {  

    public static UseIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UseIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var useArgs = UseArgs.read(_data, i);
      return new UseIxData(discriminator, useArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(useArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(useArgs);
    }
  }

  public static final Discriminator VERIFY_DISCRIMINATOR = toDiscriminator(133, 161, 141, 48, 120, 198, 88, 150);

  public static Instruction verify(final AccountMeta invokedTokenMetadataProgramMeta,
                                   // Creator to verify, collection update authority or delegate
                                   final PublicKey authorityKey,
                                   // Delegate record PDA
                                   final PublicKey delegateRecordKey,
                                   // Metadata account
                                   final PublicKey metadataKey,
                                   // Mint of the Collection
                                   final PublicKey collectionMintKey,
                                   // Metadata Account of the Collection
                                   final PublicKey collectionMetadataKey,
                                   // Master Edition Account of the Collection Token
                                   final PublicKey collectionMasterEditionKey,
                                   // System program
                                   final PublicKey systemProgramKey,
                                   // Instructions sysvar account
                                   final PublicKey sysvarInstructionsKey,
                                   final VerificationArgs verificationArgs) {
    final var keys = List.of(
      createReadOnlySigner(authorityKey),
      createRead(requireNonNullElse(delegateRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(metadataKey),
      createRead(requireNonNullElse(collectionMintKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(requireNonNullElse(collectionMetadataKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(collectionMasterEditionKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(verificationArgs)];
    int i = VERIFY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(verificationArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record VerifyIxData(Discriminator discriminator, VerificationArgs verificationArgs) implements Borsh {  

    public static VerifyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static VerifyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var verificationArgs = VerificationArgs.read(_data, i);
      return new VerifyIxData(discriminator, verificationArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(verificationArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UNVERIFY_DISCRIMINATOR = toDiscriminator(55, 1, 25, 88, 115, 67, 20, 24);

  public static Instruction unverify(final AccountMeta invokedTokenMetadataProgramMeta,
                                     // Creator to verify, collection (or metadata if parent burned) update authority or delegate
                                     final PublicKey authorityKey,
                                     // Delegate record PDA
                                     final PublicKey delegateRecordKey,
                                     // Metadata account
                                     final PublicKey metadataKey,
                                     // Mint of the Collection
                                     final PublicKey collectionMintKey,
                                     // Metadata Account of the Collection
                                     final PublicKey collectionMetadataKey,
                                     // System program
                                     final PublicKey systemProgramKey,
                                     // Instructions sysvar account
                                     final PublicKey sysvarInstructionsKey,
                                     final VerificationArgs verificationArgs) {
    final var keys = List.of(
      createReadOnlySigner(authorityKey),
      createRead(requireNonNullElse(delegateRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(metadataKey),
      createRead(requireNonNullElse(collectionMintKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(requireNonNullElse(collectionMetadataKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(systemProgramKey),
      createRead(sysvarInstructionsKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(verificationArgs)];
    int i = UNVERIFY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(verificationArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record UnverifyIxData(Discriminator discriminator, VerificationArgs verificationArgs) implements Borsh {  

    public static UnverifyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static UnverifyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var verificationArgs = VerificationArgs.read(_data, i);
      return new UnverifyIxData(discriminator, verificationArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(verificationArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator COLLECT_DISCRIMINATOR = toDiscriminator(208, 47, 194, 155, 17, 98, 82, 236);

  public static Instruction collect(final AccountMeta invokedTokenMetadataProgramMeta,
                                    // Authority to collect fees
                                    final PublicKey authorityKey,
                                    // The account to transfer collected fees to
                                    final PublicKey recipientKey) {
    final var keys = List.of(
      createReadOnlySigner(authorityKey),
      createRead(recipientKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, COLLECT_DISCRIMINATOR);
  }

  public static final Discriminator PRINT_DISCRIMINATOR = toDiscriminator(195, 207, 47, 76, 90, 172, 115, 105);

  public static Instruction print(final AccountMeta invokedTokenMetadataProgramMeta,
                                  // New Metadata key (pda of ['metadata', program id, mint id])
                                  final PublicKey editionMetadataKey,
                                  // New Edition (pda of ['metadata', program id, mint id, 'edition'])
                                  final PublicKey editionKey,
                                  // Mint of new token - THIS WILL TRANSFER AUTHORITY AWAY FROM THIS KEY
                                  final PublicKey editionMintKey,
                                  // Owner of the token account of new token
                                  final PublicKey editionTokenAccountOwnerKey,
                                  // Token account of new token
                                  final PublicKey editionTokenAccountKey,
                                  // Mint authority of new mint
                                  final PublicKey editionMintAuthorityKey,
                                  // Token record account
                                  final PublicKey editionTokenRecordKey,
                                  // Master Record Edition V2 (pda of ['metadata', program id, master metadata mint id, 'edition'])
                                  final PublicKey masterEditionKey,
                                  // Edition pda to mark creation - will be checked for pre-existence. (pda of ['metadata', program id, master metadata mint id, 'edition', edition_number]) where edition_number is NOT the edition number you pass in args but actually edition_number = floor(edition/EDITION_MARKER_BIT_SIZE).
                                  final PublicKey editionMarkerPdaKey,
                                  // payer
                                  final PublicKey payerKey,
                                  // owner of token account containing master token
                                  final PublicKey masterTokenAccountOwnerKey,
                                  // token account containing token from master metadata mint
                                  final PublicKey masterTokenAccountKey,
                                  // Master record metadata account
                                  final PublicKey masterMetadataKey,
                                  // The update authority of the master edition.
                                  final PublicKey updateAuthorityKey,
                                  // Token program
                                  final PublicKey splTokenProgramKey,
                                  // SPL Associated Token Account program
                                  final PublicKey splAtaProgramKey,
                                  // Instructions sysvar account
                                  final PublicKey sysvarInstructionsKey,
                                  // System program
                                  final PublicKey systemProgramKey,
                                  final PrintArgs printArgs) {
    final var keys = List.of(
      createWrite(editionMetadataKey),
      createWrite(editionKey),
      createWrite(editionMintKey),
      createRead(editionTokenAccountOwnerKey),
      createWrite(editionTokenAccountKey),
      createReadOnlySigner(editionMintAuthorityKey),
      createWrite(requireNonNullElse(editionTokenRecordKey, invokedTokenMetadataProgramMeta.publicKey())),
      createWrite(masterEditionKey),
      createWrite(editionMarkerPdaKey),
      createWritableSigner(payerKey),
      createRead(masterTokenAccountOwnerKey),
      createRead(masterTokenAccountKey),
      createRead(masterMetadataKey),
      createRead(updateAuthorityKey),
      createRead(splTokenProgramKey),
      createRead(splAtaProgramKey),
      createRead(sysvarInstructionsKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(printArgs)];
    int i = PRINT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(printArgs, _data, i);

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, _data);
  }

  public record PrintIxData(Discriminator discriminator, PrintArgs printArgs) implements Borsh {  

    public static PrintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static PrintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var printArgs = PrintArgs.read(_data, i);
      return new PrintIxData(discriminator, printArgs);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(printArgs, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(printArgs);
    }
  }

  public static final Discriminator RESIZE_DISCRIMINATOR = toDiscriminator(74, 27, 74, 155, 56, 134, 175, 125);

  public static Instruction resize(final AccountMeta invokedTokenMetadataProgramMeta,
                                   // The metadata account of the digital asset
                                   final PublicKey metadataKey,
                                   // The master edition or edition account of the digital asset, an uninitialized account for fungible assets
                                   final PublicKey editionKey,
                                   // Mint of token asset
                                   final PublicKey mintKey,
                                   // The recipient of the excess rent and authority if the authority account is not present
                                   final PublicKey payerKey,
                                   // Owner of the asset for (p)NFTs, or mint authority for fungible assets, if different from the payer
                                   final PublicKey authorityKey,
                                   // Token or Associated Token account
                                   final PublicKey tokenKey,
                                   // System program
                                   final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createWrite(editionKey),
      createRead(mintKey),
      createWrite(payerKey),
      createReadOnlySigner(requireNonNullElse(authorityKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(requireNonNullElse(tokenKey, invokedTokenMetadataProgramMeta.publicKey())),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, RESIZE_DISCRIMINATOR);
  }

  public static final Discriminator CLOSE_ACCOUNTS_DISCRIMINATOR = toDiscriminator(171, 222, 94, 233, 34, 250, 202, 1);

  public static Instruction closeAccounts(final AccountMeta invokedTokenMetadataProgramMeta,
                                          // Metadata (pda of ['metadata', program id, mint id])
                                          final PublicKey metadataKey,
                                          // Edition of the asset
                                          final PublicKey editionKey,
                                          // Mint of token asset
                                          final PublicKey mintKey,
                                          // Authority to close ownerless accounts
                                          final PublicKey authorityKey,
                                          // The destination account that will receive the rent.
                                          final PublicKey destinationKey) {
    final var keys = List.of(
      createWrite(metadataKey),
      createWrite(editionKey),
      createWrite(mintKey),
      createReadOnlySigner(authorityKey),
      createWrite(destinationKey)
    );

    return Instruction.createInstruction(invokedTokenMetadataProgramMeta, keys, CLOSE_ACCOUNTS_DISCRIMINATOR);
  }

  private TokenMetadataProgram() {
  }
}
