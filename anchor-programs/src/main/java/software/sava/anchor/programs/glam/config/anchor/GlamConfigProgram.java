package software.sava.anchor.programs.glam.config.anchor;

import java.util.List;

import software.sava.anchor.programs.glam.config.anchor.types.OracleSource;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class GlamConfigProgram {

  public static final Discriminator ADD_ASSET_META_DISCRIMINATOR = toDiscriminator(36, 10, 172, 139, 10, 221, 102, 77);

  public static Instruction addAssetMeta(final AccountMeta invokedGlamConfigProgramMeta,
                                         final SolanaAccounts solanaAccounts,
                                         final PublicKey globalConfigKey,
                                         final PublicKey adminKey,
                                         final PublicKey asset,
                                         final int decimals,
                                         final PublicKey oracle,
                                         final OracleSource oracleSource) {
    final var keys = List.of(
      createWrite(globalConfigKey),
      createWritableSigner(adminKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[73 + Borsh.len(oracleSource)];
    int i = writeDiscriminator(ADD_ASSET_META_DISCRIMINATOR, _data, 0);
    asset.write(_data, i);
    i += 32;
    _data[i] = (byte) decimals;
    ++i;
    oracle.write(_data, i);
    i += 32;
    Borsh.write(oracleSource, _data, i);

    return Instruction.createInstruction(invokedGlamConfigProgramMeta, keys, _data);
  }

  public record AddAssetMetaIxData(Discriminator discriminator,
                                   PublicKey asset,
                                   int decimals,
                                   PublicKey oracle,
                                   OracleSource oracleSource) implements Borsh {  

    public static AddAssetMetaIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 74;

    public static AddAssetMetaIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var asset = readPubKey(_data, i);
      i += 32;
      final var decimals = _data[i] & 0xFF;
      ++i;
      final var oracle = readPubKey(_data, i);
      i += 32;
      final var oracleSource = OracleSource.read(_data, i);
      return new AddAssetMetaIxData(discriminator,
                                    asset,
                                    decimals,
                                    oracle,
                                    oracleSource);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      asset.write(_data, i);
      i += 32;
      _data[i] = (byte) decimals;
      ++i;
      oracle.write(_data, i);
      i += 32;
      i += Borsh.write(oracleSource, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DELETE_ASSET_META_DISCRIMINATOR = toDiscriminator(108, 173, 149, 99, 144, 203, 21, 115);

  public static Instruction deleteAssetMeta(final AccountMeta invokedGlamConfigProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey globalConfigKey,
                                            final PublicKey adminKey,
                                            final PublicKey asset) {
    final var keys = List.of(
      createWrite(globalConfigKey),
      createWritableSigner(adminKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(DELETE_ASSET_META_DISCRIMINATOR, _data, 0);
    asset.write(_data, i);

    return Instruction.createInstruction(invokedGlamConfigProgramMeta, keys, _data);
  }

  public record DeleteAssetMetaIxData(Discriminator discriminator, PublicKey asset) implements Borsh {  

    public static DeleteAssetMetaIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static DeleteAssetMetaIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var asset = readPubKey(_data, i);
      return new DeleteAssetMetaIxData(discriminator, asset);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      asset.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_DISCRIMINATOR = toDiscriminator(175, 175, 109, 31, 13, 152, 155, 237);

  public static Instruction initialize(final AccountMeta invokedGlamConfigProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey globalConfigKey,
                                       final PublicKey payerKey,
                                       final PublicKey admin,
                                       final PublicKey feeAuthority,
                                       final PublicKey referrer,
                                       final int baseFeeBps,
                                       final int flowFeeBps) {
    final var keys = List.of(
      createWrite(globalConfigKey),
      createWritableSigner(payerKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[108];
    int i = writeDiscriminator(INITIALIZE_DISCRIMINATOR, _data, 0);
    admin.write(_data, i);
    i += 32;
    feeAuthority.write(_data, i);
    i += 32;
    referrer.write(_data, i);
    i += 32;
    putInt16LE(_data, i, baseFeeBps);
    i += 2;
    putInt16LE(_data, i, flowFeeBps);

    return Instruction.createInstruction(invokedGlamConfigProgramMeta, keys, _data);
  }

  public record InitializeIxData(Discriminator discriminator,
                                 PublicKey admin,
                                 PublicKey feeAuthority,
                                 PublicKey referrer,
                                 int baseFeeBps,
                                 int flowFeeBps) implements Borsh {  

    public static InitializeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 108;

    public static InitializeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var admin = readPubKey(_data, i);
      i += 32;
      final var feeAuthority = readPubKey(_data, i);
      i += 32;
      final var referrer = readPubKey(_data, i);
      i += 32;
      final var baseFeeBps = getInt16LE(_data, i);
      i += 2;
      final var flowFeeBps = getInt16LE(_data, i);
      return new InitializeIxData(discriminator,
                                  admin,
                                  feeAuthority,
                                  referrer,
                                  baseFeeBps,
                                  flowFeeBps);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      admin.write(_data, i);
      i += 32;
      feeAuthority.write(_data, i);
      i += 32;
      referrer.write(_data, i);
      i += 32;
      putInt16LE(_data, i, baseFeeBps);
      i += 2;
      putInt16LE(_data, i, flowFeeBps);
      i += 2;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_ADMIN_DISCRIMINATOR = toDiscriminator(161, 176, 40, 213, 60, 184, 179, 228);

  public static Instruction updateAdmin(final AccountMeta invokedGlamConfigProgramMeta,
                                        final SolanaAccounts solanaAccounts,
                                        final PublicKey globalConfigKey,
                                        final PublicKey adminKey,
                                        final PublicKey newAdmin) {
    final var keys = List.of(
      createWrite(globalConfigKey),
      createWritableSigner(adminKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(UPDATE_ADMIN_DISCRIMINATOR, _data, 0);
    newAdmin.write(_data, i);

    return Instruction.createInstruction(invokedGlamConfigProgramMeta, keys, _data);
  }

  public record UpdateAdminIxData(Discriminator discriminator, PublicKey newAdmin) implements Borsh {  

    public static UpdateAdminIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static UpdateAdminIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newAdmin = readPubKey(_data, i);
      return new UpdateAdminIxData(discriminator, newAdmin);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      newAdmin.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_ASSET_META_DISCRIMINATOR = toDiscriminator(223, 61, 171, 60, 126, 37, 49, 45);

  public static Instruction updateAssetMeta(final AccountMeta invokedGlamConfigProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey globalConfigKey,
                                            final PublicKey adminKey,
                                            final PublicKey asset,
                                            final PublicKey oracle,
                                            final OracleSource oracleSource) {
    final var keys = List.of(
      createWrite(globalConfigKey),
      createWritableSigner(adminKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[72 + Borsh.len(oracleSource)];
    int i = writeDiscriminator(UPDATE_ASSET_META_DISCRIMINATOR, _data, 0);
    asset.write(_data, i);
    i += 32;
    oracle.write(_data, i);
    i += 32;
    Borsh.write(oracleSource, _data, i);

    return Instruction.createInstruction(invokedGlamConfigProgramMeta, keys, _data);
  }

  public record UpdateAssetMetaIxData(Discriminator discriminator,
                                      PublicKey asset,
                                      PublicKey oracle,
                                      OracleSource oracleSource) implements Borsh {  

    public static UpdateAssetMetaIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 73;

    public static UpdateAssetMetaIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var asset = readPubKey(_data, i);
      i += 32;
      final var oracle = readPubKey(_data, i);
      i += 32;
      final var oracleSource = OracleSource.read(_data, i);
      return new UpdateAssetMetaIxData(discriminator, asset, oracle, oracleSource);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      asset.write(_data, i);
      i += 32;
      oracle.write(_data, i);
      i += 32;
      i += Borsh.write(oracleSource, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_PROTOCOL_FEES_DISCRIMINATOR = toDiscriminator(158, 219, 253, 143, 54, 45, 113, 182);

  public static Instruction updateProtocolFees(final AccountMeta invokedGlamConfigProgramMeta,
                                               final PublicKey globalConfigKey,
                                               final PublicKey feeAuthorityKey,
                                               final int baseFeeBps,
                                               final int flowFeeBps) {
    final var keys = List.of(
      createWrite(globalConfigKey),
      createReadOnlySigner(feeAuthorityKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(UPDATE_PROTOCOL_FEES_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, baseFeeBps);
    i += 2;
    putInt16LE(_data, i, flowFeeBps);

    return Instruction.createInstruction(invokedGlamConfigProgramMeta, keys, _data);
  }

  public record UpdateProtocolFeesIxData(Discriminator discriminator, int baseFeeBps, int flowFeeBps) implements Borsh {  

    public static UpdateProtocolFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 12;

    public static UpdateProtocolFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var baseFeeBps = getInt16LE(_data, i);
      i += 2;
      final var flowFeeBps = getInt16LE(_data, i);
      return new UpdateProtocolFeesIxData(discriminator, baseFeeBps, flowFeeBps);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt16LE(_data, i, baseFeeBps);
      i += 2;
      putInt16LE(_data, i, flowFeeBps);
      i += 2;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_REFERRER_DISCRIMINATOR = toDiscriminator(208, 225, 56, 15, 244, 21, 195, 34);

  public static Instruction updateReferrer(final AccountMeta invokedGlamConfigProgramMeta,
                                           final PublicKey globalConfigKey,
                                           final PublicKey feeAuthorityKey,
                                           final PublicKey referrer) {
    final var keys = List.of(
      createWrite(globalConfigKey),
      createReadOnlySigner(feeAuthorityKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(UPDATE_REFERRER_DISCRIMINATOR, _data, 0);
    referrer.write(_data, i);

    return Instruction.createInstruction(invokedGlamConfigProgramMeta, keys, _data);
  }

  public record UpdateReferrerIxData(Discriminator discriminator, PublicKey referrer) implements Borsh {  

    public static UpdateReferrerIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static UpdateReferrerIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var referrer = readPubKey(_data, i);
      return new UpdateReferrerIxData(discriminator, referrer);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      referrer.write(_data, i);
      i += 32;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  private GlamConfigProgram() {
  }
}
