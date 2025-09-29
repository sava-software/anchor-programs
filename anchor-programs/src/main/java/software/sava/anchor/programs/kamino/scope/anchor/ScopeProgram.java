package software.sava.anchor.programs.kamino.scope.anchor;

import java.lang.String;

import java.util.List;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.nio.charset.StandardCharsets.UTF_8;

import static java.util.Objects.requireNonNullElse;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class ScopeProgram {

  public static final Discriminator INITIALIZE_DISCRIMINATOR = toDiscriminator(175, 175, 109, 31, 13, 152, 155, 237);

  public static Instruction initialize(final AccountMeta invokedScopeProgramMeta,
                                       final PublicKey adminKey,
                                       final PublicKey systemProgramKey,
                                       final PublicKey configurationKey,
                                       final PublicKey tokenMetadatasKey,
                                       final PublicKey oracleTwapsKey,
                                       final PublicKey oraclePricesKey,
                                       final PublicKey oracleMappingsKey,
                                       final String feedName) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createRead(systemProgramKey),
      createWrite(configurationKey),
      createWrite(tokenMetadatasKey),
      createWrite(oracleTwapsKey),
      createWrite(oraclePricesKey),
      createWrite(oracleMappingsKey)
    );

    final byte[] _feedName = feedName.getBytes(UTF_8);
    final byte[] _data = new byte[12 + Borsh.lenVector(_feedName)];
    int i = INITIALIZE_DISCRIMINATOR.write(_data, 0);
    Borsh.writeVector(_feedName, _data, i);

    return Instruction.createInstruction(invokedScopeProgramMeta, keys, _data);
  }

  public record InitializeIxData(Discriminator discriminator, String feedName, byte[] _feedName) implements Borsh {  

    public static InitializeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeIxData createRecord(final Discriminator discriminator, final String feedName) {
      return new InitializeIxData(discriminator, feedName, feedName.getBytes(UTF_8));
    }

    public static InitializeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var feedName = Borsh.string(_data, i);
      return new InitializeIxData(discriminator, feedName, feedName.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(_feedName, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(_feedName);
    }
  }

  public static final Discriminator REFRESH_PRICE_LIST_DISCRIMINATOR = toDiscriminator(83, 186, 207, 131, 203, 254, 198, 130);

  public static Instruction refreshPriceList(final AccountMeta invokedScopeProgramMeta,
                                             final PublicKey oraclePricesKey,
                                             final PublicKey oracleMappingsKey,
                                             final PublicKey oracleTwapsKey,
                                             final PublicKey instructionSysvarAccountInfoKey,
                                             final short[] tokens) {
    final var keys = List.of(
      createWrite(oraclePricesKey),
      createRead(oracleMappingsKey),
      createWrite(oracleTwapsKey),
      createRead(instructionSysvarAccountInfoKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(tokens)];
    int i = REFRESH_PRICE_LIST_DISCRIMINATOR.write(_data, 0);
    Borsh.writeVector(tokens, _data, i);

    return Instruction.createInstruction(invokedScopeProgramMeta, keys, _data);
  }

  public record RefreshPriceListIxData(Discriminator discriminator, short[] tokens) implements Borsh {  

    public static RefreshPriceListIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static RefreshPriceListIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var tokens = Borsh.readshortVector(_data, i);
      return new RefreshPriceListIxData(discriminator, tokens);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(tokens, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(tokens);
    }
  }

  public static final Discriminator REFRESH_CHAINLINK_PRICE_DISCRIMINATOR = toDiscriminator(97, 9, 20, 115, 72, 255, 4, 140);

  public static Instruction refreshChainlinkPrice(final AccountMeta invokedScopeProgramMeta,
                                                  // The account that signs the transaction.
                                                  final PublicKey userKey,
                                                  final PublicKey oraclePricesKey,
                                                  final PublicKey oracleMappingsKey,
                                                  final PublicKey oracleTwapsKey,
                                                  // The Verifier Account stores the DON's public keys and other verification parameters.
                                                  // This account must match the PDA derived from the verifier program.
                                                  final PublicKey verifierAccountKey,
                                                  // The Access Controller Account
                                                  final PublicKey accessControllerKey,
                                                  // The Config Account is a PDA derived from a signed report
                                                  final PublicKey configAccountKey,
                                                  // The Verifier Program ID specifies the target Chainlink Data Streams Verifier Program.
                                                  final PublicKey verifierProgramIdKey,
                                                  final int token,
                                                  final byte[] serializedChainlinkReport) {
    final var keys = List.of(
      createReadOnlySigner(userKey),
      createWrite(oraclePricesKey),
      createRead(oracleMappingsKey),
      createWrite(oracleTwapsKey),
      createRead(verifierAccountKey),
      createRead(accessControllerKey),
      createRead(configAccountKey),
      createRead(verifierProgramIdKey)
    );

    final byte[] _data = new byte[10 + Borsh.lenVector(serializedChainlinkReport)];
    int i = REFRESH_CHAINLINK_PRICE_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, token);
    i += 2;
    Borsh.writeVector(serializedChainlinkReport, _data, i);

    return Instruction.createInstruction(invokedScopeProgramMeta, keys, _data);
  }

  public record RefreshChainlinkPriceIxData(Discriminator discriminator, int token, byte[] serializedChainlinkReport) implements Borsh {  

    public static RefreshChainlinkPriceIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static RefreshChainlinkPriceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var token = getInt16LE(_data, i);
      i += 2;
      final var serializedChainlinkReport = Borsh.readbyteVector(_data, i);
      return new RefreshChainlinkPriceIxData(discriminator, token, serializedChainlinkReport);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, token);
      i += 2;
      i += Borsh.writeVector(serializedChainlinkReport, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 2 + Borsh.lenVector(serializedChainlinkReport);
    }
  }

  public static final Discriminator REFRESH_PYTH_LAZER_PRICE_DISCRIMINATOR = toDiscriminator(122, 47, 177, 133, 177, 35, 93, 118);

  // IMPORTANT: we assume the tokens passed in to this ix are in the same order in which
  // they are found in the message payload. Thus, we rely on the client to do this work
  public static Instruction refreshPythLazerPrice(final AccountMeta invokedScopeProgramMeta,
                                                  // The account that signs the transaction.
                                                  final PublicKey userKey,
                                                  final PublicKey oraclePricesKey,
                                                  final PublicKey oracleMappingsKey,
                                                  final PublicKey oracleTwapsKey,
                                                  final PublicKey pythProgramKey,
                                                  final PublicKey pythStorageKey,
                                                  final PublicKey pythTreasuryKey,
                                                  final PublicKey systemProgramKey,
                                                  final PublicKey instructionsSysvarKey,
                                                  final short[] tokens,
                                                  final byte[] serializedPythMessage,
                                                  final int ed25519InstructionIndex) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(oraclePricesKey),
      createRead(oracleMappingsKey),
      createWrite(oracleTwapsKey),
      createRead(pythProgramKey),
      createRead(pythStorageKey),
      createWrite(pythTreasuryKey),
      createRead(systemProgramKey),
      createRead(instructionsSysvarKey)
    );

    final byte[] _data = new byte[10 + Borsh.lenVector(tokens) + Borsh.lenVector(serializedPythMessage)];
    int i = REFRESH_PYTH_LAZER_PRICE_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeVector(tokens, _data, i);
    i += Borsh.writeVector(serializedPythMessage, _data, i);
    putInt16LE(_data, i, ed25519InstructionIndex);

    return Instruction.createInstruction(invokedScopeProgramMeta, keys, _data);
  }

  public record RefreshPythLazerPriceIxData(Discriminator discriminator,
                                            short[] tokens,
                                            byte[] serializedPythMessage,
                                            int ed25519InstructionIndex) implements Borsh {  

    public static RefreshPythLazerPriceIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static RefreshPythLazerPriceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var tokens = Borsh.readshortVector(_data, i);
      i += Borsh.lenVector(tokens);
      final var serializedPythMessage = Borsh.readbyteVector(_data, i);
      i += Borsh.lenVector(serializedPythMessage);
      final var ed25519InstructionIndex = getInt16LE(_data, i);
      return new RefreshPythLazerPriceIxData(discriminator, tokens, serializedPythMessage, ed25519InstructionIndex);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(tokens, _data, i);
      i += Borsh.writeVector(serializedPythMessage, _data, i);
      putInt16LE(_data, i, ed25519InstructionIndex);
      i += 2;
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(tokens) + Borsh.lenVector(serializedPythMessage) + 2;
    }
  }

  public static final Discriminator UPDATE_MAPPING_DISCRIMINATOR = toDiscriminator(56, 102, 90, 236, 243, 21, 185, 105);

  public static Instruction updateMapping(final AccountMeta invokedScopeProgramMeta,
                                          final PublicKey adminKey,
                                          final PublicKey configurationKey,
                                          final PublicKey oracleMappingsKey,
                                          final PublicKey priceInfoKey,
                                          final int token,
                                          final int priceType,
                                          final boolean twapEnabled,
                                          final int twapSource,
                                          final int refPriceIndex,
                                          final String feedName,
                                          final byte[] genericData) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(configurationKey),
      createWrite(oracleMappingsKey),
      createRead(requireNonNullElse(priceInfoKey, invokedScopeProgramMeta.publicKey()))
    );

    final byte[] _feedName = feedName.getBytes(UTF_8);
    final byte[] _data = new byte[20 + Borsh.lenVector(_feedName) + Borsh.lenArray(genericData)];
    int i = UPDATE_MAPPING_DISCRIMINATOR.write(_data, 0);
    putInt16LE(_data, i, token);
    i += 2;
    _data[i] = (byte) priceType;
    ++i;
    _data[i] = (byte) (twapEnabled ? 1 : 0);
    ++i;
    putInt16LE(_data, i, twapSource);
    i += 2;
    putInt16LE(_data, i, refPriceIndex);
    i += 2;
    i += Borsh.writeVector(_feedName, _data, i);
    Borsh.writeArrayChecked(genericData, 20, _data, i);

    return Instruction.createInstruction(invokedScopeProgramMeta, keys, _data);
  }

  public record UpdateMappingIxData(Discriminator discriminator,
                                    int token,
                                    int priceType,
                                    boolean twapEnabled,
                                    int twapSource,
                                    int refPriceIndex,
                                    String feedName, byte[] _feedName,
                                    byte[] genericData) implements Borsh {  

    public static UpdateMappingIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int GENERIC_DATA_LEN = 20;
    public static UpdateMappingIxData createRecord(final Discriminator discriminator,
                                                   final int token,
                                                   final int priceType,
                                                   final boolean twapEnabled,
                                                   final int twapSource,
                                                   final int refPriceIndex,
                                                   final String feedName,
                                                   final byte[] genericData) {
      return new UpdateMappingIxData(discriminator,
                                     token,
                                     priceType,
                                     twapEnabled,
                                     twapSource,
                                     refPriceIndex,
                                     feedName, feedName.getBytes(UTF_8),
                                     genericData);
    }

    public static UpdateMappingIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var token = getInt16LE(_data, i);
      i += 2;
      final var priceType = _data[i] & 0xFF;
      ++i;
      final var twapEnabled = _data[i] == 1;
      ++i;
      final var twapSource = getInt16LE(_data, i);
      i += 2;
      final var refPriceIndex = getInt16LE(_data, i);
      i += 2;
      final var feedName = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var genericData = new byte[20];
      Borsh.readArray(genericData, _data, i);
      return new UpdateMappingIxData(discriminator,
                                     token,
                                     priceType,
                                     twapEnabled,
                                     twapSource,
                                     refPriceIndex,
                                     feedName, feedName.getBytes(UTF_8),
                                     genericData);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, token);
      i += 2;
      _data[i] = (byte) priceType;
      ++i;
      _data[i] = (byte) (twapEnabled ? 1 : 0);
      ++i;
      putInt16LE(_data, i, twapSource);
      i += 2;
      putInt16LE(_data, i, refPriceIndex);
      i += 2;
      i += Borsh.writeVector(_feedName, _data, i);
      i += Borsh.writeArrayChecked(genericData, 20, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 2
           + 1
           + 1
           + 2
           + 2
           + Borsh.lenVector(_feedName)
           + Borsh.lenArray(genericData);
    }
  }

  public static final Discriminator RESET_TWAP_DISCRIMINATOR = toDiscriminator(101, 216, 28, 92, 154, 79, 49, 187);

  public static Instruction resetTwap(final AccountMeta invokedScopeProgramMeta,
                                      final PublicKey adminKey,
                                      final PublicKey configurationKey,
                                      final PublicKey oracleTwapsKey,
                                      final PublicKey instructionSysvarAccountInfoKey,
                                      final long token,
                                      final String feedName) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(configurationKey),
      createWrite(oracleTwapsKey),
      createRead(instructionSysvarAccountInfoKey)
    );

    final byte[] _feedName = feedName.getBytes(UTF_8);
    final byte[] _data = new byte[20 + Borsh.lenVector(_feedName)];
    int i = RESET_TWAP_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, token);
    i += 8;
    Borsh.writeVector(_feedName, _data, i);

    return Instruction.createInstruction(invokedScopeProgramMeta, keys, _data);
  }

  public record ResetTwapIxData(Discriminator discriminator, long token, String feedName, byte[] _feedName) implements Borsh {  

    public static ResetTwapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ResetTwapIxData createRecord(final Discriminator discriminator, final long token, final String feedName) {
      return new ResetTwapIxData(discriminator, token, feedName, feedName.getBytes(UTF_8));
    }

    public static ResetTwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var token = getInt64LE(_data, i);
      i += 8;
      final var feedName = Borsh.string(_data, i);
      return new ResetTwapIxData(discriminator, token, feedName, feedName.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, token);
      i += 8;
      i += Borsh.writeVector(_feedName, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + Borsh.lenVector(_feedName);
    }
  }

  public static final Discriminator UPDATE_TOKEN_METADATA_DISCRIMINATOR = toDiscriminator(243, 6, 8, 23, 126, 181, 251, 158);

  public static Instruction updateTokenMetadata(final AccountMeta invokedScopeProgramMeta,
                                                final PublicKey adminKey,
                                                final PublicKey configurationKey,
                                                final PublicKey tokensMetadataKey,
                                                final long index,
                                                final long mode,
                                                final String feedName,
                                                final byte[] value) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(configurationKey),
      createWrite(tokensMetadataKey)
    );

    final byte[] _feedName = feedName.getBytes(UTF_8);
    final byte[] _data = new byte[28 + Borsh.lenVector(_feedName) + Borsh.lenVector(value)];
    int i = UPDATE_TOKEN_METADATA_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, index);
    i += 8;
    putInt64LE(_data, i, mode);
    i += 8;
    i += Borsh.writeVector(_feedName, _data, i);
    Borsh.writeVector(value, _data, i);

    return Instruction.createInstruction(invokedScopeProgramMeta, keys, _data);
  }

  public record UpdateTokenMetadataIxData(Discriminator discriminator,
                                          long index,
                                          long mode,
                                          String feedName, byte[] _feedName,
                                          byte[] value) implements Borsh {  

    public static UpdateTokenMetadataIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateTokenMetadataIxData createRecord(final Discriminator discriminator,
                                                         final long index,
                                                         final long mode,
                                                         final String feedName,
                                                         final byte[] value) {
      return new UpdateTokenMetadataIxData(discriminator,
                                           index,
                                           mode,
                                           feedName, feedName.getBytes(UTF_8),
                                           value);
    }

    public static UpdateTokenMetadataIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var index = getInt64LE(_data, i);
      i += 8;
      final var mode = getInt64LE(_data, i);
      i += 8;
      final var feedName = Borsh.string(_data, i);
      i += (Integer.BYTES + getInt32LE(_data, i));
      final var value = Borsh.readbyteVector(_data, i);
      return new UpdateTokenMetadataIxData(discriminator,
                                           index,
                                           mode,
                                           feedName, feedName.getBytes(UTF_8),
                                           value);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, index);
      i += 8;
      putInt64LE(_data, i, mode);
      i += 8;
      i += Borsh.writeVector(_feedName, _data, i);
      i += Borsh.writeVector(value, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 8 + 8 + Borsh.lenVector(_feedName) + Borsh.lenVector(value);
    }
  }

  public static final Discriminator SET_ADMIN_CACHED_DISCRIMINATOR = toDiscriminator(114, 14, 105, 205, 216, 148, 30, 75);

  public static Instruction setAdminCached(final AccountMeta invokedScopeProgramMeta,
                                           final PublicKey adminKey,
                                           final PublicKey configurationKey,
                                           final PublicKey newAdmin,
                                           final String feedName) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(configurationKey)
    );

    final byte[] _feedName = feedName.getBytes(UTF_8);
    final byte[] _data = new byte[44 + Borsh.lenVector(_feedName)];
    int i = SET_ADMIN_CACHED_DISCRIMINATOR.write(_data, 0);
    newAdmin.write(_data, i);
    i += 32;
    Borsh.writeVector(_feedName, _data, i);

    return Instruction.createInstruction(invokedScopeProgramMeta, keys, _data);
  }

  public record SetAdminCachedIxData(Discriminator discriminator, PublicKey newAdmin, String feedName, byte[] _feedName) implements Borsh {  

    public static SetAdminCachedIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SetAdminCachedIxData createRecord(final Discriminator discriminator, final PublicKey newAdmin, final String feedName) {
      return new SetAdminCachedIxData(discriminator, newAdmin, feedName, feedName.getBytes(UTF_8));
    }

    public static SetAdminCachedIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newAdmin = readPubKey(_data, i);
      i += 32;
      final var feedName = Borsh.string(_data, i);
      return new SetAdminCachedIxData(discriminator, newAdmin, feedName, feedName.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      newAdmin.write(_data, i);
      i += 32;
      i += Borsh.writeVector(_feedName, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 32 + Borsh.lenVector(_feedName);
    }
  }

  public static final Discriminator APPROVE_ADMIN_CACHED_DISCRIMINATOR = toDiscriminator(101, 149, 97, 58, 48, 79, 16, 105);

  public static Instruction approveAdminCached(final AccountMeta invokedScopeProgramMeta,
                                               final PublicKey adminCachedKey,
                                               final PublicKey configurationKey,
                                               final String feedName) {
    final var keys = List.of(
      createReadOnlySigner(adminCachedKey),
      createWrite(configurationKey)
    );

    final byte[] _feedName = feedName.getBytes(UTF_8);
    final byte[] _data = new byte[12 + Borsh.lenVector(_feedName)];
    int i = APPROVE_ADMIN_CACHED_DISCRIMINATOR.write(_data, 0);
    Borsh.writeVector(_feedName, _data, i);

    return Instruction.createInstruction(invokedScopeProgramMeta, keys, _data);
  }

  public record ApproveAdminCachedIxData(Discriminator discriminator, String feedName, byte[] _feedName) implements Borsh {  

    public static ApproveAdminCachedIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static ApproveAdminCachedIxData createRecord(final Discriminator discriminator, final String feedName) {
      return new ApproveAdminCachedIxData(discriminator, feedName, feedName.getBytes(UTF_8));
    }

    public static ApproveAdminCachedIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var feedName = Borsh.string(_data, i);
      return new ApproveAdminCachedIxData(discriminator, feedName, feedName.getBytes(UTF_8));
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(_feedName, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(_feedName);
    }
  }

  public static final Discriminator CREATE_MINT_MAP_DISCRIMINATOR = toDiscriminator(216, 218, 224, 60, 23, 31, 193, 243);

  public static Instruction createMintMap(final AccountMeta invokedScopeProgramMeta,
                                          final PublicKey adminKey,
                                          final PublicKey configurationKey,
                                          final PublicKey mappingsKey,
                                          final PublicKey systemProgramKey,
                                          final PublicKey seedPk,
                                          final long seedId,
                                          final int bump,
                                          final short[][] scopeChains) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createRead(configurationKey),
      createWrite(mappingsKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[49 + Borsh.lenVectorArray(scopeChains)];
    int i = CREATE_MINT_MAP_DISCRIMINATOR.write(_data, 0);
    seedPk.write(_data, i);
    i += 32;
    putInt64LE(_data, i, seedId);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    Borsh.writeVectorArrayChecked(scopeChains, 4, _data, i);

    return Instruction.createInstruction(invokedScopeProgramMeta, keys, _data);
  }

  public record CreateMintMapIxData(Discriminator discriminator,
                                    PublicKey seedPk,
                                    long seedId,
                                    int bump,
                                    short[][] scopeChains) implements Borsh {  

    public static CreateMintMapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreateMintMapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var seedPk = readPubKey(_data, i);
      i += 32;
      final var seedId = getInt64LE(_data, i);
      i += 8;
      final var bump = _data[i] & 0xFF;
      ++i;
      final var scopeChains = Borsh.readMultiDimensionshortVectorArray(4, _data, i);
      return new CreateMintMapIxData(discriminator,
                                     seedPk,
                                     seedId,
                                     bump,
                                     scopeChains);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      seedPk.write(_data, i);
      i += 32;
      putInt64LE(_data, i, seedId);
      i += 8;
      _data[i] = (byte) bump;
      ++i;
      i += Borsh.writeVectorArrayChecked(scopeChains, 4, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 32 + 8 + 1 + Borsh.lenVectorArray(scopeChains);
    }
  }

  public static final Discriminator CLOSE_MINT_MAP_DISCRIMINATOR = toDiscriminator(146, 212, 203, 239, 191, 104, 38, 102);

  public static Instruction closeMintMap(final AccountMeta invokedScopeProgramMeta,
                                         final PublicKey adminKey,
                                         final PublicKey configurationKey,
                                         final PublicKey mappingsKey,
                                         final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createRead(configurationKey),
      createWrite(mappingsKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedScopeProgramMeta, keys, CLOSE_MINT_MAP_DISCRIMINATOR);
  }

  private ScopeProgram() {
  }
}
